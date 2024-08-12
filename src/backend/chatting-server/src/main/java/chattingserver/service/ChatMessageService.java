package chattingserver.service;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.room.Music;
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
import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final RoomRepository roomRepository;
    private final EntityToResponseDtoConverter converter;

    private static final int PAGE_SIZE = 20;

    public ChatMessageDto saveChatMessage(ChatMessageDto dto) {
        ChatMessage message = ChatMessage.from(dto);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        return converter.convertMessage(savedMessage);
    }

    public List<ChatMessageResponseDto> getNewMessages(String roomId, String readMsgId) {
        return chatMessageRepository.getNewMessages(roomId, readMsgId).stream()
                .map(converter::convertToResponseMessage)
                .collect(Collectors.toList());
    }

    public List<ChatMessageResponseDto> getAllMessagesAtRoom(String roomId) {
        return chatMessageRepository.getAllMessagesAtRoom(roomId).stream()
                .map(converter::convertToResponseMessage)
                .collect(Collectors.toList());
    }

    public Page<ChatMessageResponseDto> chatMessagePagination(String roomId, int page) {
        return chatMessageRepository.findByRoomIdWithPagingAndFiltering(roomId, page, PAGE_SIZE)
                .map(converter::convertToResponseMessage);
    }

    @Transactional
    public ChatMessageDto join(ChatMessageDto dto) {
        Room room = getRoomOrThrow(dto.getRoomId());
        ensureRoomCapacity(dto.getSenderId());

        ChatMessage message = createEntranceMessage(dto);
        ChatMessage savedMessage = chatMessageRepository.save(message);

        User joinedUser = createJoinedUser(dto, savedMessage.getId());
        roomRepository.addUserToRoom(room.getId(), joinedUser);

        ChatMessageDto resultDto = converter.convertMessage(savedMessage);
        resultDto.setCurrentMusicId(getCurrentMusicId(room.getId()));
        return resultDto;
    }

    private Room getRoomOrThrow(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("존재하지 않는 채팅방입니다. 채팅방 id=" + roomId, ErrorCode.UNKNOWN_ERROR));
    }

    private void ensureRoomCapacity(Long userId) {
        List<Room> joinedRooms = roomRepository.findJoinedRoomsByUid(userId);
        if (joinedRooms.size() == 4) {
            String oldestRoomId = joinedRooms.get(3).getId();
            roomRepository.exitRoom(oldestRoomId, userId);
        }
    }

    private ChatMessage createEntranceMessage(ChatMessageDto dto) {
        return ChatMessage.builder()
                .messageType(MessageType.ENTRANCE)
                .roomId(dto.getRoomId())
                .senderId(dto.getSenderId())
                .nickName(dto.getNickName())
                .content(dto.getNickName() + "님이 입장하셨습니다.")
                .senderProfileImage(dto.getSenderProfileImage())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private User createJoinedUser(ChatMessageDto dto, String lastReadMessageId) {
        return User.builder()
                .uid(dto.getSenderId())
                .nickName(dto.getNickName())
                .profileImage(dto.getSenderProfileImage())
                .enteredAt(LocalDateTime.now())
                .lastReadMessageId(lastReadMessageId)
                .build();
    }

    public Long getCurrentMusicId(String roomId) {
        Room room = getRoomOrThrow(roomId);
        Duration elapsedTime = Duration.between(room.getCreatedAt(), LocalDateTime.now());
        Duration totalPlaylistTime = room.getPlaylistDuration();

        long currentPlaylistTimeInSeconds = elapsedTime.abs().getSeconds() % totalPlaylistTime.getSeconds();

        return room.getPlaylist().getMusics().stream()
                .reduce(new MusicAccumulator(Duration.ZERO, null),
                        (acc, music) -> {
                            acc.playlistTime = acc.playlistTime.plus(Duration.parse(music.getPlaytime()));
                            if (acc.playlistTime.getSeconds() >= currentPlaylistTimeInSeconds && acc.musicId == null) {
                                acc.musicId = music.getId();
                            }
                            return acc;
                        },
                        (acc1, acc2) -> acc1)
                .musicId;
    }

    private static class MusicAccumulator {
        Duration playlistTime;
        Long musicId;

        MusicAccumulator(Duration playlistTime, Long musicId) {
            this.playlistTime = playlistTime;
            this.musicId = musicId;
        }
    }
}