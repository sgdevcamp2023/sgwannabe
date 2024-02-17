package com.lalala.streaming.handler

import com.lalala.exception.BusinessException
import com.lalala.exception.ErrorCode
import com.lalala.response.BaseResponse
import com.lalala.streaming.constant.StreamingConstant
import com.lalala.streaming.dto.MusicDetailDTO
import com.lalala.streaming.handler.Command.*
import io.github.oshai.kotlinlogging.KotlinLogging
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.floor

class StreamingHandler(
    private val ffMpeg: FFmpeg,
    private val ffProbe: FFprobe,
    @Qualifier("musicClient")
    private val musicClient: RestClient,
    @Qualifier("storageClient")
    private val storageClient: RestClient,
) : TextWebSocketHandler() {
    // TODO: 다음 트랙 변화 이벤트 추가

    private val logger = KotlinLogging.logger {}

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "${session.id} - Connected!" }
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "${session.id} - Disconnected! ( ${status.code} - ${status.reason} )" }

        File(StreamingConstant.TEMP_FOLDER)
            .walkTopDown()
            .maxDepth(1)
            .filter(File::isFile)
            .filter { it.name.startsWith(session.id) }
            .forEach(File::delete)

        super.afterConnectionClosed(session, status)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

        val payload = message.payload

        logger.info { "${session.id} - ${payload}" }

        val command = payload.split("/")[0]
        val commandType = Command.entries.find { it.value == command } ?: throw RuntimeException()

        try {
            when (commandType) {
                GET -> {
                    val musicId = payload.split("/")[1]
                    sendPlayTime(session, musicId)
                }

                STREAM -> {
                    val musicId = payload.split("/")[1]
                    val startTime = payload.split("/")[2]
                    startStream(session, musicId, startTime)
                }

                TRACK_CHANGED -> TODO()
            }
        } catch (err: Exception) {
            err.printStackTrace()
            afterConnectionClosed(session, CloseStatus.GOING_AWAY)
        }
    }

    fun getMusic(session: WebSocketSession, musicId: String): BaseResponse<MusicDetailDTO> {
        return musicClient.get()
            .uri("/v1/api/musics/${musicId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, _ ->
                session.sendMessage(TextMessage("failed music"))
                throw BusinessException(ErrorCode.MUSIC_NOT_FOUND)
            }
            .onStatus(HttpStatusCode::is5xxServerError) { _, _ ->
                session.sendMessage(TextMessage("failed music"))
                throw BusinessException(ErrorCode.MUSIC_NOT_FOUND)
            }
            .body<BaseResponse<MusicDetailDTO>>()!!
    }

    fun sendPlayTime(session: WebSocketSession, musicId: String) {
        // 음원 서버에서 파일 주소 조회
        val music = getMusic(session, musicId).data
        music.playTime.let { session.sendMessage(TextMessage("$it")) }
    }

    fun startStream(session: WebSocketSession, musicId: String, startTime: String) {
        // 음원 서버에서 파일 주소 조회
        val music = getMusic(session, musicId).data
        val fileName = music.file.fileUrl.split('/').last()

        // 파일 서버에서 파일 다운로드
        downloadMusic(session, fileName)

        val mediaFile = File("${StreamingConstant.TEMP_FOLDER}/${session.id}.mp3")

        val result = ffProbe.probe(mediaFile.path)
        val format = result.getFormat()
        val duration = format.duration

        logger.info { "filename: " + format.filename }
        logger.info { "duration : " + format.duration }
        logger.info { "bit_rate : " + format.bit_rate }

        val processBuilder = ProcessBuilder()
        processBuilder.directory(File(System.getProperty("user.dir")));
        processBuilder.command(
            ffMpeg.path, "-y", "-i", mediaFile.path,
            "-ss", startTime, "-f", "segment", "-segment_time", "${StreamingConstant.TRIM_DURATION_SEC}",
            "${StreamingConstant.TEMP_FOLDER}/${session.id}-%d.flac"
        )

        val commandLineThread = Thread() {
            try {
                val process: Process = processBuilder.start()
                process.waitFor(10, TimeUnit.SECONDS)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        commandLineThread.isDaemon = true
        commandLineThread.start()

        val remainingDuration = duration - startTime.toDouble()
        val remainingCount = floor(remainingDuration / StreamingConstant.TRIM_DURATION_SEC).toInt()

        for (i in 0..remainingCount) {
            val nextFile = File(StreamingConstant.TEMP_FOLDER, "${session.id}-${i+1}.flac")
            while (!nextFile.exists()) {
                if (!commandLineThread.isAlive && i == remainingCount) {
                    // 마지막은 다음 파일이 나오는 기준이 아닌, 스레드 종료 기준
                    break;
                }
                Thread.sleep(10)
            }

            val splitFile = File(StreamingConstant.TEMP_FOLDER, "${session.id}-${i}.flac")
            splitFile.inputStream().buffered().use { stream ->
                session.sendMessage(BinaryMessage(stream.readBytes(), false))
            }
            session.sendMessage(BinaryMessage(ByteArray(0), true))

            splitFile.delete()

            // 다음 전달까지 대기
            Thread.sleep(100);
        }

        mediaFile.delete()
    }

    fun downloadMusic(session: WebSocketSession, fileName: String) {
        // 파일 서버에서 파일 다운로드
        val storageData = storageClient.get()
            .uri("/${fileName}")
            .accept(MediaType.APPLICATION_OCTET_STREAM)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, _ ->
                session.sendMessage(TextMessage("failed storage"))
                throw BusinessException(ErrorCode.STORAGE_FILE_NOT_FOUND)
            }
            .onStatus(HttpStatusCode::is5xxServerError) { _, _ ->
                session.sendMessage(TextMessage("failed storage"))
                throw BusinessException(ErrorCode.STORAGE_FILE_NOT_FOUND)
            }
            .body<ByteArray>()

        val downloadFile = File(StreamingConstant.TEMP_FOLDER, "${session.id}.mp3")
        downloadFile.writeBytes(storageData!!)
    }
}
