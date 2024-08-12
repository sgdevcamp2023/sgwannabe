package chattingserver.config.kafka;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.request.IndexingRequestMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producers {
    @Value("${kafka.topic.chat-name}")
    private String topicChatName;

    @Value("${kafka.topic.room-name}")
    private String topicRoomName;

    private final KafkaTemplate<String, ChatMessageDto> chatKafkaTemplate;
    private final KafkaTemplate<String, IndexingRequestMessageDto> roomKafkaTemplate;
    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    public void sendMessage(ChatMessageDto chatMessageDto) {
        if (chatMessageDto.getMessageType() == MessageType.CREATION) {
            RoomResponseDto roomResponseDto = roomService.getRoomInfo(chatMessageDto.getRoomId());
            sendRoomMessage(createIndexingRequestMessage(roomResponseDto));
        } else {
            sendChatMessage(chatMessageDto);
        }
    }

    public void sendRoomMessage(IndexingRequestMessageDto roomMessageDto) {
        roomKafkaTemplate.send(topicRoomName, roomMessageDto)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleSendFailure("Room", roomMessageDto.getRoomId(), ex);
                    }
                });
    }

    private void sendChatMessage(ChatMessageDto chatMessageDto) {
        chatKafkaTemplate.send(topicChatName, chatMessageDto)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleSendFailure("Chat", chatMessageDto.getId(), ex);
                        chatMessageService.deleteChat(chatMessageDto.getId());
                    }
                });
    }

    private void handleSendFailure(String messageType, String messageId, Throwable ex) {
        throw new KafkaException(messageType + " 메시지 전송 실패: " + messageId, ex);
    }

    private IndexingRequestMessageDto createIndexingRequestMessage(RoomResponseDto room) {
        return IndexingRequestMessageDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .playlistId(room.getPlaylist().getId())
                .thumbnailImage(room.getThumbnailImage())
                .build();
    }
}