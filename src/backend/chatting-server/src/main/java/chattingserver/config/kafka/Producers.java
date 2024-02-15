package chattingserver.config.kafka;

import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.RoomMessageDto;
import chattingserver.dto.request.IndexingRequestMessageDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.dto.response.UserListResponseDto;
import chattingserver.service.ChatMessageService;
import chattingserver.service.RoomService;
import chattingserver.util.constant.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producers {
    @Value("${kafka.topic.chat-name}")
    private String topicChatName;

    private final KafkaTemplate<String, ChatMessageDto> chatKafkaTemplate;

    @Value("${kafka.topic.room-name}")
    private String topicRoomName;

    private final KafkaTemplate<String, IndexingRequestMessageDto> roomKafkaTemplate;

    private final ChatMessageService chatMessageService;
    private final RoomService roomService;

    public void sendMessage(ChatMessageDto chatMessageDto) {
        if (chatMessageDto.getMessageType() == MessageType.CREATION) {
            RoomResponseDto roomResponseDto = roomService.getRoomInfo(chatMessageDto.getRoomId());
            List<Long> receivers = roomResponseDto.getUsers().stream().map(UserListResponseDto::getUid).collect(Collectors.toList());
            receivers.remove(chatMessageDto.getSenderId());
            sendRoomMessage(IndexingRequestMessageDto.builder()
                    .roomId(roomResponseDto.getId())
                    .roomName(roomResponseDto.getRoomName())
                    .playlistId(roomResponseDto.getPlaylist().getId())
                    .thumbnailImage(roomResponseDto.getThumbnailImage())
                    .build());
        } else {

            CompletableFuture<SendResult<String, ChatMessageDto>> completableFuture = chatKafkaTemplate.send(topicChatName, chatMessageDto);
            completableFuture.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("채팅방 id: {}, 발신자 id: {}, 메시지: {}", chatMessageDto.getRoomId(), chatMessageDto.getSenderId(), chatMessageDto.getContent());
                } else {
                    log.error("메시지 전송 불가=[" + chatMessageDto.getContent() + "] 원인 : " + ex.getMessage());
                    chatMessageService.deleteChat(chatMessageDto.getId());
                    log.info("삭제된 메시지={}", chatMessageDto.getId());
                }
            });
        }
    }

    public void sendRoomMessage(IndexingRequestMessageDto roomMessageDto) {
        CompletableFuture<SendResult<String, IndexingRequestMessageDto>> completableFuture = roomKafkaTemplate.send(topicRoomName, roomMessageDto);
        completableFuture.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("메시지 전송 성공=[" + roomMessageDto.getRoomId() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                log.info("roomMessageDto={}", roomMessageDto.toString());
            } else {
                log.info("메시지 전송 불가=[" + roomMessageDto.getRoomId() + "] 원인 : " + ex.getMessage());
            }
        });
    }

}
