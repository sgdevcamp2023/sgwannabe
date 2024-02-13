package chattingserver.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.RoomMessageDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.producer.room-producer.key-serializer}")
    private String roomKeySerializer;

    @Value("${spring.kafka.producer.room-producer.value-serializer}")
    private String roomValueSerializer;

    @Value("${spring.kafka.producer.chat-producer.key-serializer}")
    private String chatKeySerializer;

    @Value("${spring.kafka.producer.chat-producer.value-serializer}")
    private String chatValueSerializer;

    @Bean
    public ProducerFactory<String, ChatMessageDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(chatProducerConfigurations());
    }

    @Bean
    public Map<String, Object> chatProducerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }

    @Bean
    public KafkaTemplate<String, ChatMessageDto> chatKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, RoomMessageDto> roomProducerFactory() {
        return new DefaultKafkaProducerFactory<>(roomProducerConfigurations());
    }

    @Bean
    public Map<String, Object> roomProducerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configurations;
    }

    @Bean
    public KafkaTemplate<String, RoomMessageDto> roomKafkaTemplate() {
        return new KafkaTemplate<>(roomProducerFactory());
    }
}
