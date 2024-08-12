package chattingserver.service;

import chattingserver.domain.chat.ChatMessage;
import chattingserver.domain.chat.LastMessage;
import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.dto.request.ReadMessageUpdateRequestDto;
import chattingserver.dto.request.RoomCreateRequestDto;
import chattingserver.dto.response.CommonAPIMessage;
import chattingserver.dto.response.JoinedRoomResponseDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.repository.ChatMessageRepository;
import chattingserver.repository.RoomRepository;
import chattingserver.util.converter.EntityToResponseDtoConverter;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EntityToResponseDtoConverter converter;

    public RoomResponseDto getRoomInfo(String roomId) {
        return roomRepository.findById(roomId)
                .map(converter::convertRoom)
                .orElse(null);
    }

    public List<JoinedRoomResponseDto> findJoinedRoomsByUid(Long uid) {
        return roomRepository.findJoinedRoomsByUid(uid).stream()
                .map(this::createJoinedRoomResponseDto)
                .collect(Collectors.toList());
    }

    private JoinedRoomResponseDto createJoinedRoomResponseDto(Room room) {
        ChatMessage lastMessage = chatMessageRepository.getLastMessage(room.getId());
        LastMessage lastMessageDto = createLastMessageDto(lastMessage);

        return JoinedRoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .userCount(room.getUsers().size())
                .users(room.getUsers())
                .playlistOwner(converter.convertUser(room.getPlaylistOwner()))
                .playlist(room.getPlaylist())
                .thumbnailImage(room.getThumbnailImage())
                .lastMessage(lastMessageDto)
                .build();
    }

    private LastMessage createLastMessageDto(ChatMessage message) {
        return LastMessage.builder()
                .messageId(message.getId())
                .senderId(message.getSenderId())
                .nickName(message.getNickName())
                .senderProfileImage(message.getSenderProfileImage())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public List<RoomResponseDto> findUnjoinedRooms(Long uid) {
        return roomRepository.findUnjoinedRoomsSortedByCreationDate(uid, Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(this::createRoomResponseDto)
                .collect(Collectors.toList());
    }

    private RoomResponseDto createRoomResponseDto(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .thumbnailImage(room.getThumbnailImage())
                .userCount(room.getUsers().size())
                .users(room.getUsers().stream().map(converter::convertUser).collect(Collectors.toList()))
                .playlist(room.getPlaylist())
                .playlistOwner(converter.convertUser(room.getPlaylistOwner()))
                .build();
    }

    public RoomResponseDto create(RoomCreateRequestDto dto) {
        User owner = createOwner(dto);
        Room room = createRoom(dto, owner);
        Room savedRoom = roomRepository.save(room);
        return converter.convertRoom(savedRoom);
    }

    private User createOwner(RoomCreateRequestDto dto) {
        return User.builder()
                .uid(dto.getUid())
                .nickName(dto.getNickName())
                .profileImage(dto.getUserProfileImage())
                .enteredAt(LocalDateTime.now())
                .build();
    }

    private Room createRoom(RoomCreateRequestDto dto, User owner) {
        return Room.builder()
                .roomName(dto.getPlaylist().getName())
                .playlist(dto.getPlaylist())
                .playlistDuration(dto.getPlaylist().getTotalPlaylistTime())
                .thumbnailImage(dto.getThumbnailImage())
                .playlistOwner(owner)
                .users(Collections.singletonList(owner))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public boolean exitRoom(String roomId, Long uid) {
        try {
            roomRepository.exitRoom(roomId, uid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CommonAPIMessage updateLastReadMsgId(ReadMessageUpdateRequestDto requestDto) {
        UpdateResult updateResult = roomRepository.updateLastReadMsgId(requestDto);
        return new CommonAPIMessage(
                updateResult.getModifiedCount() > 0 ? CommonAPIMessage.ResultEnum.success : CommonAPIMessage.ResultEnum.failed,
                updateResult.getModifiedCount()
        );
    }

    public boolean isExistingRoom(String roomId) {
        return roomRepository.existsById(roomId);
    }

    public List<RoomResponseDto> getAllRoomInfos() {
        return roomRepository.findAll().stream()
                .map(converter::convertRoom)
                .collect(Collectors.toList());
    }
}
