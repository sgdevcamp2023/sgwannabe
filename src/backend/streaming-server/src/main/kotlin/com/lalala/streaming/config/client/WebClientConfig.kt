package com.lalala.streaming.config.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

const val UNIT_MB = 1024 * 1024

@Component
class WebClientConfig(
    @Value("\${music.size-mb}") val sizeMB: Int,
    @Value("\${client.endpoint.music}") val musicUrl: String,
    @Value("\${client.endpoint.storage}") val storageUrl: String
) {
    val STRATEGIES = ExchangeStrategies.builder()
        .codecs { it.defaultCodecs().maxInMemorySize(sizeMB * UNIT_MB) }
        .build()

    @Bean
    @LoadBalanced
    fun musicClient() = WebClient.builder()
        .baseUrl(musicUrl)
        .exchangeStrategies(STRATEGIES)

    @Bean
    fun storageClient() = WebClient.builder()
        .baseUrl(storageUrl)
        .exchangeStrategies(STRATEGIES)
}
