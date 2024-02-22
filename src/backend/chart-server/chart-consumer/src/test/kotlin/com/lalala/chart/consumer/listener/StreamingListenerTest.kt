package com.lalala.chart.consumer.listener

import com.lalala.event.StreamingCompleteEvent
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka

@SpringBootTest
@EmbeddedKafka
internal class StreamingListenerTest {
    @Autowired
    private val eventConsumer: StreamingListener? = null

    @Test
    fun consumePaintCreatedEvent() {
        val event: StreamingCompleteEvent = generatedEvent()
        eventConsumer?.consumeStreamingComplete(event)
    }

    companion object {
        private fun generatedEvent(): StreamingCompleteEvent {
            return StreamingCompleteEvent(
                1L,
            )
        }
    }
}
