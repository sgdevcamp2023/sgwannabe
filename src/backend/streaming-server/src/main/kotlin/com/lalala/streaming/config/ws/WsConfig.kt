package com.lalala.streaming.config.ws

import com.lalala.streaming.handler.StreamingHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping


const val STREAMING_URL = "/v1/ws/streaming"

@Configuration
class WsConfig(
    private val streamingHandler: StreamingHandler
) {
    @Bean
    fun streamingHandlerMapping(): HandlerMapping {
        return SimpleUrlHandlerMapping(
            mapOf(Pair(STREAMING_URL, streamingHandler)),
            1
        )
    }
}
