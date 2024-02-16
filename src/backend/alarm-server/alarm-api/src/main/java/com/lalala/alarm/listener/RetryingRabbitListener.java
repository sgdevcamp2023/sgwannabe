package com.lalala.alarm.listener;

import static com.lalala.alarm.config.RabbitMQConfig.QUEUE;
import static com.lalala.alarm.config.RabbitMQConfig.UNDELIVERED_QUEUE;

import com.lalala.alarm.dto.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class RetryingRabbitListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.retry.count}")
    private Integer retryCount;

    @RabbitListener(queues = QUEUE)
    public void primary(Email email, @Header(required = false, name = "x-death") Map<String, ?> xDeath) throws Exception {
        log.info("Message read from Queue: " + email);
        if (checkRetryCount(xDeath)) {
            sendToUndelivered(email);
            return;
        }

        throw new Exception("Random error");
    }


    private boolean checkRetryCount(Map<String, ?> xDeath) {

        if (xDeath != null && !xDeath.isEmpty()) {
            Long count = (Long) xDeath.get("count");
            return count >= retryCount;
        }

        return false;
    }

    private void sendToUndelivered(Email email) {
        log.warn("maximum retry reached, send message to the undelivered queue, msg: {}", email);
        this.rabbitTemplate.convertAndSend(UNDELIVERED_QUEUE, email);
    }
}