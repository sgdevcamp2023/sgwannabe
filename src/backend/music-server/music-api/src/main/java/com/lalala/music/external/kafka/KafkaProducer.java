package com.lalala.music.external.kafka;

import com.lalala.event.LalalaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, LalalaEvent> template;

    @Async
    public void execute(LalalaEvent event) {
        template.send(event.getTopic(), event);
    }
}
