package com.lalala.streaming.config.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class RestClientConfig(
    @Value("\${client.endpoint.music:http://localhost:24000}") val musicUrl: String,
    @Value("\${client.endpoint.storage:http://localhost:30000}") val storageUrl: String
) {
    @Bean(name = ["musicClient"])
    fun musicRestClient() = RestClient.builder()
        .baseUrl(musicUrl)
        .build()

    @Bean(name = ["storageClient"])
    fun storageRestClient() = RestClient.builder()
        .baseUrl(storageUrl)
        .build()
}
