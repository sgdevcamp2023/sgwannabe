package chattingserver.config.kafka;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.RoomMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.chat-consumer.group-id}")
    private String chatGroupId;
    @Value("${spring.kafka.consumer.chat-consumer.auto-offset-reset}")
    private String chatAutoOffsetResetConfig;

    @Value("${spring.kafka.consumer.room-consumer.group-id}")
    private String roomGroupId;
    @Value("${spring.kafka.consumer.room-consumer.auto-offset-reset}")
    private String roomAutoOffsetResetConfig;

    @Bean
    public ConsumerFactory<String, ChatMessageDto> chatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                chatConsumerConfigurations(),
                new StringDeserializer(),
                new JsonDeserializer<>(ChatMessageDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RoomMessageDto> roomConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                roomConsumerConfigurations(),
                new StringDeserializer(),
                new JsonDeserializer<>(RoomMessageDto.class)
        );
    }

    private Map<String, Object> chatConsumerConfigurations() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, chatGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, chatAutoOffsetResetConfig);
        return config;
    }

    private Map<String, Object> roomConsumerConfigurations() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, roomGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, roomAutoOffsetResetConfig);
        return config;
    }
}
