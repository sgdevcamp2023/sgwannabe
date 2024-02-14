package chattingserver.service;

import chattingserver.domain.room.User;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.repository.RoomRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.repository.ChatMessageRepository;
import chattingserver.util.constant.MessageType;
import chattingserver.util.converter.EntityToResponseDtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatMessageService {

    private final ObjectMapper objectMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final EntityToResponseDtoConverter entityToResponseDtoConverter;

    private final RoomService roomService;

    private static final int SIZE = 12;

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ChatMessageResponseDto saveChatMessage(ChatMessageDto chatMessageDto) {
        RoomResponseDto room = roomService.getRoomInfo(chatMessageDto.getRoomId());

        ChatMessage message =
                chatMessageRepository.save(
                        ChatMessage.builder()
                                .messageType(chatMessageDto.getMessageType())
                                .roomId(chatMessageDto.getRoomId())
                                .senderId(chatMessageDto.getSenderId())
                                .content(chatMessageDto.getContent())
                                .createdAt(LocalDateTime.now())
                                .build());

        log.info("메시지 저장 성공 message={}", message);

        return entityToResponseDtoConverter.convertMessage(message, room.getLeader());
    }

    public Object getNewMessages(String roomId, String readMsgId) {
        RoomResponseDto room = roomService.getRoomInfo(roomId);

        List<ChatMessage> messages = chatMessageRepository.getNewMessages(roomId, readMsgId);
        log.info("신규 메시지 조회 성공 roomId={}, readMsgId={}", roomId, readMsgId);
        return messages.stream()
                .map(chatMessage -> entityToResponseDtoConverter.convertMessage(chatMessage, room.getLeader()))
                .collect(Collectors.toList());
    }

    public List<ChatMessageResponseDto> getAllMessagesAtRoom(String roomId) {
        RoomResponseDto room = roomService.getRoomInfo(roomId);

        return chatMessageRepository.getAllMessagesAtRoom(roomId).stream()
                .map(chatMessage -> entityToResponseDtoConverter.convertMessage(chatMessage, room.getLeader()))
                .collect(Collectors.toList());
    }

    public Page<ChatMessageResponseDto> chatMessagePagination(String roomId, int page) {
        RoomResponseDto room = roomService.getRoomInfo(roomId);

        Page<ChatMessage> messagePage =
                chatMessageRepository.findByRoomIdWithPagingAndFiltering(roomId, page, SIZE);
        log.info("특정 채팅방 메시지 페이지네이션 조회 성공 roomId={}", roomId);
        return messagePage.map(
                new Function<ChatMessage, ChatMessageResponseDto>() {
                    @Override
                    public ChatMessageResponseDto apply(ChatMessage message) {
                        return entityToResponseDtoConverter.convertMessage(message, room.getLeader());
                    }
                });
    }

    public void deleteChat(String id) {
        chatMessageRepository.deleteById(id);
    }

    public ChatMessageDto join(ChatMessageDto chatMessageDto) {

        ChatMessage message =
                chatMessageRepository.save(
                        ChatMessage.builder()
                                .messageType(MessageType.ENTRANCE)
                                .roomId(chatMessageDto.getRoomId())
                                .senderId(chatMessageDto.getSenderId())
                                .content(chatMessageDto.getSenderId() + "님이 입장하셨습니다.")
                                .createdAt(LocalDateTime.now())
                                .build());

        return ChatMessageDto.builder()
                .id(message.getId())
                .messageType(message.getMessageType())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public ChatMessageDto leave(ChatMessageDto chatMessageDto) {

        ChatMessage message =
                chatMessageRepository.save(
                        ChatMessage.builder()
                                .messageType(MessageType.ENTRANCE)
                                .roomId(chatMessageDto.getRoomId())
                                .senderId(chatMessageDto.getSenderId())
                                .content(chatMessageDto.getSenderId() + "님이 퇴장하셨습니다.")
                                .createdAt(LocalDateTime.now())
                                .build());

        return ChatMessageDto.builder()
                .id(message.getId())
                .messageType(message.getMessageType())
                .roomId(message.getRoomId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
