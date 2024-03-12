package com.lalala.streaming.handler

import com.lalala.event.StreamingCompleteEvent
import com.lalala.exception.BusinessException
import com.lalala.exception.ErrorCode
import com.lalala.response.BaseResponse
import com.lalala.streaming.constant.StreamingConstant
import com.lalala.streaming.dto.MusicDetailDTO
import com.lalala.streaming.external.kafka.KafkaProducer
import com.lalala.streaming.handler.Command.*
import io.github.oshai.kotlinlogging.KotlinLogging
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.pathString
import kotlin.math.floor


inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

@Component
class StreamingHandler(
    private val ffMpeg: FFmpeg,
    private val ffProbe: FFprobe,
    @Qualifier("musicClient") private val musicClient: WebClient.Builder,
    @Qualifier("storageClient") private val storageClient: WebClient.Builder,
    private val producer: KafkaProducer,
    @Qualifier("preprocessingExecutor") private val executor: Executor,
) : WebSocketHandler {
    private val logger = KotlinLogging.logger {}

    override fun handle(session: WebSocketSession): Mono<Void> {
        val messages = session.receive()
            .doOnSubscribe { onConnect(session) }
            .subscribeOn(Schedulers.parallel())
            .map { it.payloadAsText }
            .flatMap { handleTextMessage(session, it) }
            .onErrorResume {
                if (it is BusinessException) {
                    when (it.errorCode) {
                        ErrorCode.MUSIC_NOT_FOUND -> Flux.just(session.textMessage("failed music"))
                        ErrorCode.STORAGE_FILE_NOT_FOUND -> Flux.just(session.textMessage("failed storage"))
                        else -> Flux.just(session.textMessage("unknown error"))
                    }
                }
                Flux.just(session.textMessage("unknown error"))
            }

        return session.send(messages)
            .subscribeOn(Schedulers.parallel())
            .doOnError { it.printStackTrace() }
            .doFinally { onDisconnect(session) }
    }

    fun onConnect(session: WebSocketSession) {
        logger.info { "${session.id} - Connected!" }
    }

    fun onDisconnect(session: WebSocketSession) {
        logger.info { "${session.id} - Disconnected!" }

        File(StreamingConstant.TEMP_FOLDER)
            .walkTopDown()
            .maxDepth(1)
            .toFlux()
            .filter { it.isFile && it.name.startsWith(session.id) }
            .doOnNext(File::delete)
            .subscribe()
    }

    fun handleTextMessage(session: WebSocketSession, payload: String): Flux<WebSocketMessage> {
        logger.info { "${session.id} - ${payload}" }

        val command = payload.split("/")[0]
        val commandType = Command.entries.find { it.value == command } ?: throw RuntimeException()

        when (commandType) {
            GET -> {
                val musicId = payload.split("/")[1]
                return sendPlayTime(session, musicId)
            }

            STREAM -> {
                val musicId = payload.split("/")[1]
                val startTime = payload.split("/")[2]
                return startStream(session, musicId, startTime)
            }

            TRACK_CHANGED -> {
                val completeMusicId = payload.split("/")[1]
                producer.execute(StreamingCompleteEvent(completeMusicId.toLong()))
                return Flux.just(session.textMessage("track changed"))
            }
        }
    }

    fun getMusic(session: WebSocketSession, musicId: String): Mono<MusicDetailDTO> {
        return musicClient.build().get()
            .uri("/v1/api/musics/${musicId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError) {
                Mono.error(BusinessException(ErrorCode.MUSIC_NOT_FOUND))
            }
            .bodyToMono(typeReference<BaseResponse<MusicDetailDTO>>())
            .map {
                it.data as MusicDetailDTO
            }
    }

    fun sendPlayTime(session: WebSocketSession, musicId: String): Flux<WebSocketMessage> {
        // 음원 서버에서 파일 주소 조회
        return getMusic(session, musicId)
            .flatMapMany {
                Flux.just(session.textMessage("${it.playTime}"))
            }
    }

    fun startStream(session: WebSocketSession, musicId: String, startTime: String): Flux<WebSocketMessage> {
        // 음원 서버에서 파일 주소 조회
        return getMusic(session, musicId)
            .map { URI(it.file.fileUrl).path }
            .flatMap { downloadMusic(session, it) }
            .then(Paths.get("${StreamingConstant.TEMP_FOLDER}/${session.id}.mp3").toMono())
            .flatMapMany {
                val result = ffProbe.probe(it.pathString)
                val format = result.getFormat()
                val duration = format.duration

                logger.info { "filename: " + format.filename }
                logger.info { "duration : " + format.duration }
                logger.info { "bit_rate : " + format.bit_rate }

                val processBuilder = ProcessBuilder()
                processBuilder.directory(File(System.getProperty("user.dir")));
                processBuilder.command(
                    ffMpeg.path, "-y", "-i", it.pathString,
                    "-ss", startTime, "-f", "segment", "-segment_time", "${StreamingConstant.TRIM_DURATION_SEC}",
                    "${StreamingConstant.TEMP_FOLDER}/${session.id}-%d.flac"
                )

                val latch = CountDownLatch(1)
                val commandLineThread = Thread() {
                    try {
                        val process: Process = processBuilder.start()
                        process.waitFor(10, TimeUnit.SECONDS)
                        latch.countDown()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                commandLineThread.isDaemon = true
                executor.execute(commandLineThread)

                val remainingDuration = duration - startTime.toDouble()
                val remainingCount = floor(remainingDuration / StreamingConstant.TRIM_DURATION_SEC).toInt()

                return@flatMapMany Flux.generate<WebSocketMessage?, Int?>(
                    { 0 },
                    { state, sink ->
                        val nextPath = Paths.get(StreamingConstant.TEMP_FOLDER, "${session.id}-${state + 1}.flac")
                        while (!nextPath.exists()) {
                            if (state == remainingCount) {
                                // 마지막은 다음 파일이 나오는 기준이 아닌, 스레드 종료 기준
                                latch.await()
                                break;
                            }
                        }

                        val trimFilePath = Paths.get(StreamingConstant.TEMP_FOLDER, "${session.id}-${state}.flac")
                        val trimMusicInputStream = Files.newInputStream(trimFilePath)
                        trimMusicInputStream.buffered().use { stream ->
                            sink.next(session.binaryMessage { it.wrap(stream.readBytes()) })
                        }
                        trimFilePath.deleteIfExists()

                        if (state == remainingCount) {
                            sink.complete()
                        }

                        return@generate state + 1
                    }
                )
                    .doFinally {
                        Paths.get(StreamingConstant.TEMP_FOLDER, "${session.id}.mp3").deleteIfExists()
                    }
            }
    }

    fun downloadMusic(session: WebSocketSession, filePath: String): Mono<Void> {
        // 파일 서버에서 파일 다운로드
        val downloadFlux = storageClient.build().get()
            .uri(filePath)
            .accept(MediaType.APPLICATION_OCTET_STREAM)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _ ->
                Mono.error(BusinessException(ErrorCode.STORAGE_FILE_NOT_FOUND))
            }
            .bodyToFlux(DataBuffer::class.java)

        val outputPath = Paths.get(StreamingConstant.TEMP_FOLDER, "${session.id}.mp3")
        return Mono.fromRunnable<Boolean> {
            Files.deleteIfExists(outputPath)
        }
            .then(
                DataBufferUtils.write(downloadFlux, outputPath, StandardOpenOption.CREATE)
            )
    }
}
