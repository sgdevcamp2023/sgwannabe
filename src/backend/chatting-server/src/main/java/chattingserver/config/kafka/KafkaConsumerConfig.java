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

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.chat-consumer.group-id}")
    private String chatGroupId;
    @Value("${spring.kafka.consumer.chat-consumer.key-deserializer}")
    private String chatKeyDeserializer;
    @Value("${spring.kafka.consumer.chat-consumer.value-deserializer}")
    private String chatValueDeserializer;
    @Value("${spring.kafka.consumer.chat-consumer.auto-offset-reset}")
    private String chatAutoOffsetResetConfig;

    @Value("${spring.kafka.consumer.room-consumer.group-id}")
    private String roomGroupId;
    @Value("${spring.kafka.consumer.room-consumer.key-deserializer}")
    private String roomKeyDeserializer;
    @Value("${spring.kafka.consumer.room-consumer.value-deserializer}")
    private String roomValueDeserializer;
    @Value("${spring.kafka.consumer.room-consumer.auto-offset-reset}")
    private String roomAutoOffsetResetConfig;

    @Bean
    public ConsumerFactory<String, ChatMessageDto> chatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(chatConsumerConfigurations(), new StringDeserializer(),
                new JsonDeserializer<>(ChatMessageDto.class));
    }

    private Map<String, Object> chatConsumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, chatGroupId);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configurations.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, chatAutoOffsetResetConfig); // earliest: 전체 , latest: 최신 메시지
        return configurations;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RoomMessageDto> roomConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(roomConsumerConfigurations(), new StringDeserializer(),
                new JsonDeserializer<>(RoomMessageDto.class));
    }

    private Map<String, Object> roomConsumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, roomGroupId);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configurations.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, roomAutoOffsetResetConfig); // earliest: 전체 , latest: 최신 메시지
        return configurations;
    }
}