package com.lalala.chart.consumer.listener

import com.lalala.chart.consumer.service.StreamingLogService
import com.lalala.event.StreamingCompleteEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class StreamingListener(
    private val service: StreamingLogService,
) {
    @KafkaListener(
        topics = ["streaming_complete"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory",
    )
    fun consumeStreamingComplete(event: StreamingCompleteEvent) {
        service.create(event.musicId)
    }
}
