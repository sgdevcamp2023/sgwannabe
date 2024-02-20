package com.lalala.streaming.config.ws

import com.lalala.streaming.handler.StreamingHandler
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFprobe
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.socket.config.annotation.*

@Configuration
@EnableWebSocket
class WsConfig(
    private val ffMpeg: FFmpeg,
    private val ffProbe: FFprobe,
    private val musicClient: RestClient,
    @Qualifier("storageClient")
    private val storageClient: RestClient,
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(streamingHandler(), "/v1/ws/streaming")
            .setAllowedOrigins("http://localhost:3000")
        registry
            .addHandler(streamingHandler(), "/v1/ws/streaming")
            .setAllowedOrigins("http://localhost:3000")
            .withSockJS()
    }

    @Bean
    fun streamingHandler() = StreamingHandler(ffMpeg, ffProbe, musicClient, storageClient)
}
