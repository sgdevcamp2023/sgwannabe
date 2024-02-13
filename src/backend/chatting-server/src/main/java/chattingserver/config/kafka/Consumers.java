package chattingserver.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.RoomMessageDto;
import chattingserver.dto.response.RoomResponseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class Consumers {
    private final SimpMessagingTemplate template;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.chat-consumer.group-id}",
            topics = "${spring.kafka.topic.chat-name}")
    public void listenChat(ChatMessageDto chatMessageDto) {
        template.convertAndSend("/chat/topic/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.room-consumer.group-id}",
            topics = "${spring.kafka.topic.room-name}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupCreation(RoomMessageDto roomMessageDto) {
        RoomResponseDto roomResponseDto = roomMessageDto.getRoomResponseDto();
        for (Long userId : roomMessageDto.getReceivers()) {
            template.convertAndSend("/chat/topic/new-room/" + userId, roomResponseDto);
        }
    }
}
