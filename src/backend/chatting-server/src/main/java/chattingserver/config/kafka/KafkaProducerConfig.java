package chattingserver.config.kafka;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.RoomMessageDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
    @Value("${server.kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value("${server.kafka.producer.room-producer.key-serializer}")
    private String roomKeySerializer;
    @Value("${server.kafka.producer.room-producer.value-serializer}")
    private String roomValueSerializer;
    @Value("${server.kafka.producer.chat-producer.key-serializer}")
    private String chatKeySerializer;
    @Value("${server.kafka.producer.chat-producer.value-serializer}")
    private String chatValueSerializer;

    @Bean
    public ProducerFactory<String, ChatMessageDto> producerFactory(){
        return new DefaultKafkaProducerFactory<>(chatProducerConfigurations());
    }

    @Bean
    public Map<String, Object> chatProducerConfigurations(){
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, chatKeySerializer);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, chatValueSerializer);
        return configurations;
    }

    @Bean
    public KafkaTemplate<String, ChatMessageDto> chatKafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, RoomMessageDto> roomProducerFactory(){
        return new DefaultKafkaProducerFactory<>(roomProducerConfigurations());
    }

    @Bean
    public Map<String, Object> roomProducerConfigurations(){
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, roomKeySerializer);
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, roomValueSerializer);
        return configurations;
    }

    @Bean
    public KafkaTemplate<String, RoomMessageDto> roomKafkaTemplate(){
        return new KafkaTemplate<>(roomProducerFactory());
    }
}
