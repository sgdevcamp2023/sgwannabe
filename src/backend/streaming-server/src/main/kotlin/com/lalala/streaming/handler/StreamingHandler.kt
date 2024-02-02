package com.lalala.streaming.handler

import com.lalala.streaming.dto.MusicDetailDTO
import com.lalala.streaming.exception.MusicNotFoundException
import com.lalala.streaming.exception.StorageFileNotFoundException
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

val TRIM_DURATION = 3

class StreamingHandler(
    private val ffProbe: FFprobe,
    @Qualifier("musicClient")
    private val musicClient: RestClient,
    @Qualifier("storageClient")
    private val storageClient: RestClient,
) : TextWebSocketHandler() {
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        // message 에서 음원 아이디 / 시작 재생 시각 분리
        val payload = message.payload

        println(payload)

        val command = payload.split("/")[0]
        val commandType = Command.entries.find { it.value == command } ?: throw RuntimeException()

        when (commandType) {
            Command.GET -> {
                val musicId = payload.split("/")[1]

                // 음원 서버에서 파일 주소 조회
                val music = musicClient.get()
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
                    .body<MusicDetailDTO>()

                music?.playTime.let { session.sendMessage(TextMessage("$it")) }
            }
            Command.STREAM -> {
                val musicId = payload.split("/")[1]
                val startTime = payload.split("/")[2]

                // TODO: 레디스에서 미리 캐싱 해놓기, 타임아웃은 곡 끝날 때 기준 3분
                // 음원 서버에서 파일 주소 조회
                val music = musicClient.get()
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
                    .body<MusicDetailDTO>()

                val fileName = music!!.file.fileUrl.split('/').last()

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

                val downloadFile = File("${session.id}.mp3")
                downloadFile.writeBytes(storageData!!)

                val result = ffProbe.probe("${session.id}.mp3")
                val format = result.getFormat()
                val duration = format.duration

                println("filename: " + format.filename)
                println("duration : " + format.duration)
                println("bit_rate : " + format.bit_rate)

                val processBuilder = ProcessBuilder()
                processBuilder.directory(File(System.getProperty("user.dir")));
                processBuilder.command(
                    "./libs/ffmpeg-6.1/ffmpeg", "-y", "-i", "${session.id}.mp3",
                    "-ss", startTime, "-f", "segment", "-segment_time", "${TRIM_DURATION}",
                    "${session.id}-%d.flac"
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
                val remainingCount = floor(remainingDuration / TRIM_DURATION).toInt()

                println(remainingDuration)
                println(remainingCount)

                for (i in 0..remainingCount) {
                    val nextFile = File("${session.id}-${i+1}.flac")
                    while (!nextFile.exists()) {
                        if (!commandLineThread.isAlive && i == remainingCount) {
                            // 마지막은 다음 파일이 나오는 기준이 아닌, 스레드 종료 기준
                            break;
                        }
                        Thread.sleep(10)
                    }

                    val splitFile = File("${session.id}-${i}.flac")
                    splitFile.inputStream().buffered().use { stream ->
                        session.sendMessage(BinaryMessage(stream.readBytes(), false))
                    }
                    session.sendMessage(BinaryMessage(ByteArray(0), true))

                    splitFile.delete()
                    File("${session.id}-${i}.flac").delete()

                    // 다음 전달까지 대기
                    Thread.sleep(100);
                }

                File("${session.id}.mp3").delete()
            }
        }
    }

    // TODO: 연결 끊어졌을 때 해당 세션 파일 찾아서 삭제
}
