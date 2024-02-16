package chattingserver.service;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.dto.ChatMessageDto;
import chattingserver.dto.request.UserEntranceRequestDto;
import chattingserver.dto.response.ChatMessageResponseDto;
import chattingserver.repository.ChatMessageRepository;
import chattingserver.repository.RoomRepository;
import chattingserver.util.constant.MessageType;
import chattingserver.util.converter.EntityToResponseDtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatMessageService {

    private final ObjectMapper objectMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final RoomRepository roomRepository;
    private final EntityToResponseDtoConverter entityToResponseDtoConverter;


    private static final int SIZE = 20;

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ChatMessageDto saveChatMessage(ChatMessageDto chatMessageDto) {

        ChatMessage message = chatMessageRepository.save(ChatMessage.builder()
                .messageType(chatMessageDto.getMessageType())
                .roomId(chatMessageDto.getRoomId())
                .senderId(chatMessageDto.getSenderId())
                .nickName(chatMessageDto.getNickName())
                .senderProfileImage(chatMessageDto.getSenderProfileImage())
                .content(chatMessageDto.getContent())
                .createdAt(LocalDateTime.now())
                .build());

        log.info("메시지 저장 성공 message={}", message);

        return entityToResponseDtoConverter.convertMessage(message);
    }

    public List<ChatMessageResponseDto> getNewMessages(String roomId, String readMsgId) {
        List<ChatMessage> messages = chatMessageRepository.getNewMessages(roomId, readMsgId);
        log.info("신규 메시지 조회 성공 roomId={}, readMsgId={}, messages={}", roomId, readMsgId, messages);
        return messages.stream().map(entityToResponseDtoConverter::convertToResponseMessage).collect(Collectors.toList());
    }

    public List<ChatMessageResponseDto> getAllMessagesAtRoom(String roomId) {
        return chatMessageRepository.getAllMessagesAtRoom(roomId).stream().map(entityToResponseDtoConverter::convertToResponseMessage).collect(Collectors.toList());
    }

    public Page<ChatMessageResponseDto> chatMessagePagination(String roomId, int page) {
        Page<ChatMessage> messagePage =
                chatMessageRepository.findByRoomIdWithPagingAndFiltering(roomId, page, SIZE);
        log.info("특정 채팅방 메시지 페이지네이션 조회 성공 roomId={}", roomId);
        return messagePage.map(new Function<ChatMessage, ChatMessageResponseDto>() {
            @Override
            public ChatMessageResponseDto apply(ChatMessage message) {
                return entityToResponseDtoConverter.convertToResponseMessage(message);
            }
        });
    }

    public void deleteChat(String id) {
        chatMessageRepository.deleteById(id);
    }

    @Transactional
    public ChatMessageDto join(ChatMessageDto chatMessageDto) {
        try {
            Optional<Room> optionalRoom = roomRepository.findById(chatMessageDto.getRoomId());
            if (optionalRoom.isEmpty()) {
                log.info("해당하는 방이 없습니다. roomId={}", chatMessageDto.getRoomId());
                // TODO null처리
                return null;
            }

            Room room = optionalRoom.get();

            ChatMessage message = chatMessageRepository.save(ChatMessage.builder()
                    .messageType(MessageType.ENTRANCE)
                    .roomId(chatMessageDto.getRoomId())
                    .senderId(chatMessageDto.getSenderId())
                    .nickName(chatMessageDto.getNickName())
                    .content(chatMessageDto.getNickName() + "님이 입장하셨습니다.")
                    .senderProfileImage(chatMessageDto.getSenderProfileImage())
                    .createdAt(LocalDateTime.now())
                    .build());

            User joinedUser = User.builder()
                    .uid(chatMessageDto.getSenderId())
                    .nickName(chatMessageDto.getNickName())
                    .profileImage(chatMessageDto.getSenderProfileImage())
                    .enteredAt(LocalDateTime.now())
                    .lastReadMessageId(message.getId())
                    .build();

            roomRepository.addUserToRoom(room.getId(), joinedUser);

            return entityToResponseDtoConverter.convertMessage(message);
        } catch (Exception e) {
            log.error("트랜잭션 오류: {}", e.getMessage());
            // TODO 트랜잭션 롤백 또는 예외 처리 로직 추가
            throw new RuntimeException("트랜잭션 오류 발생");
        }
    }

    public ChatMessageDto permanentLeaving(String roomId, UserEntranceRequestDto userDto) {

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isEmpty()) {
            log.info("해당하는 방이 없습니다. roomId={}", roomId);
            // TODO null처리
            return null;
        }

        Room room = optionalRoom.get();
        Optional<User> optionalUser = room.getUsers().stream().filter(u -> u.getUid().equals(userDto.getUid())).findAny();
        if (optionalUser.isEmpty()) {
            log.info("해당하는 유저가 없습니다. uId={}", userDto.getUid());
            // TODO null처리
            return null;
        }

        room.getUsers().remove(optionalUser.get());

        ChatMessage chatMessage = ChatMessage.builder()
                .messageType(MessageType.ENTRANCE)
                .roomId(roomId)
                .senderId(userDto.getUid())
                .nickName(userDto.getNickName())
                .senderProfileImage(userDto.getProfileImage())
                .content(userDto.getNickName() + "님이 퇴장하셨습니다.")
                .createdAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(chatMessage);

        return entityToResponseDtoConverter.convertMessage(chatMessage);

    }

    public List<ChatMessageResponseDto> getMessagesBefore(String roomId, String readMsgId) {
        List<ChatMessage> messages = chatMessageRepository.findPreviousMessages(roomId, readMsgId, SIZE);
        log.info("이전 메시지 조회 성공 roomId={}, readMsgId={}, messages={}", roomId, readMsgId, messages);
        return messages.stream().map(entityToResponseDtoConverter::convertToResponseMessage).collect(Collectors.toList());
    }

}
