package com.lalala.streaming.external.kafka

import com.lalala.event.LalalaEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val template: KafkaTemplate<String, LalalaEvent>
) {
    @Async
    fun execute(event: LalalaEvent) {
        template.send(event.topic, event)
    }
}
