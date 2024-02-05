package com.lalala.streaming.handler

import com.lalala.streaming.constant.StreamingConstant
import com.lalala.streaming.dto.MusicDetailDTO
import com.lalala.streaming.exception.MusicNotFoundException
import com.lalala.streaming.exception.StorageFileNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.socket.BinaryMessage
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
    // TODO: 레디스 연결하여 노래 끝났는지 확인
    // TODO: 다음 트랙 변화 이벤트 추가
    // TODO: 연결 끊어졌을 때 해당 세션 파일 찾아서 삭제

    private val logger = KotlinLogging.logger {}

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

        val payload = message.payload

        logger.info { "${session.id} - ${payload}" }

        val command = payload.split("/")[0]
        val commandType = Command.entries.find { it.value == command } ?: throw RuntimeException()

        when (commandType) {
            Command.GET -> {
                val musicId = payload.split("/")[1]
                sendPlayTime(session, musicId)
            }
            Command.STREAM -> {
                val musicId = payload.split("/")[1]
                val startTime = payload.split("/")[2]
                startStream(session, musicId, startTime)
            }
        }
    }

    fun getMusic(session: WebSocketSession, musicId: String): MusicDetailDTO {
        return musicClient.get()
            .uri("/api/v1/musics/${musicId}")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, _ ->
                session.sendMessage(TextMessage("failed music"))
                throw MusicNotFoundException("음원을 조회할 수 없습니다.")
            }
            .onStatus(HttpStatusCode::is5xxServerError) { _, _ ->
                session.sendMessage(TextMessage("failed music"))
                throw MusicNotFoundException("음원을 조회할 수 없습니다.")
            }
            .body<MusicDetailDTO>()!!
    }

    fun sendPlayTime(session: WebSocketSession, musicId: String) {
        // 음원 서버에서 파일 주소 조회
        val music = getMusic(session, musicId)
        music?.playTime.let { session.sendMessage(TextMessage("$it")) }
    }

    fun startStream(session: WebSocketSession, musicId: String, startTime: String) {
        // 음원 서버에서 파일 주소 조회
        val music = getMusic(session, musicId)
        val fileName = music.file.fileUrl.split('/').last()

        // 파일 서버에서 파일 다운로드
        downloadMusic(session, fileName)

        val result = ffProbe.probe("${session.id}.mp3")
        val format = result.getFormat()
        val duration = format.duration

        logger.info { "filename: " + format.filename }
        logger.info { "duration : " + format.duration }
        logger.info { "bit_rate : " + format.bit_rate }

        val processBuilder = ProcessBuilder()
        processBuilder.directory(File(System.getProperty("user.dir")));
        processBuilder.command(
            ffMpeg.path, "-y", "-i", "${StreamingConstant.TEMP_FOLDER}/${session.id}.mp3",
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
            File(StreamingConstant.TEMP_FOLDER, "${session.id}-${i}.flac").delete()

            // 다음 전달까지 대기
            Thread.sleep(100);
        }

        File(StreamingConstant.TEMP_FOLDER, "${session.id}.mp3").delete()
    }

    fun downloadMusic(session: WebSocketSession, fileName: String) {
        // 파일 서버에서 파일 다운로드
        val storageData = storageClient.get()
            .uri("/${fileName}")
            .accept(MediaType.APPLICATION_OCTET_STREAM)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, _ ->
                session.sendMessage(TextMessage("failed storage"))
                throw StorageFileNotFoundException("음원 파일을 조회할 수 없습니다.")
            }
            .onStatus(HttpStatusCode::is5xxServerError) { _, _ ->
                session.sendMessage(TextMessage("failed storage"))
                throw StorageFileNotFoundException("음원 파일을 조회할 수 없습니다.")
            }
            .body<ByteArray>()

        val downloadFile = File(StreamingConstant.TEMP_FOLDER, "${session.id}.mp3")
        downloadFile.writeBytes(storageData!!)
    }
}
