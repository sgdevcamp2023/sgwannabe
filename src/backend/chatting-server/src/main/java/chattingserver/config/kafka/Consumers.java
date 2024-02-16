package chattingserver.config.kafka;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.request.IndexingRequestMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumers {
    private final SimpMessagingTemplate template;

    @KafkaListener(groupId = "${spring.kafka.consumer.chat-consumer.group-id}", topics = "${kafka.topic.chat-name}")
    public void listenChat(ChatMessageDto chatMessageDto) {
        template.convertAndSend("/chat/topic/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }

    // TODO search indexing test용 추후 삭제 예정
    @KafkaListener(groupId = "${spring.kafka.consumer.room-consumer.group-id}", topics = "${kafka.topic.room-name}", containerFactory = "kafkaListenerContainerFactory")
    public void listenRoomCreation(IndexingRequestMessageDto indexingRequestMessageDto) {

        template.convertAndSend("/chat/topic/search/room/index", indexingRequestMessageDto);

    }
}