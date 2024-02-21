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
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EntityToResponseDtoConverter entityToResponseDtoConverter;

    public RoomResponseDto getRoomInfo(String roomId) {
        Optional<Room> foundRoom = roomRepository.findById(roomId);
        log.info("특정 방 정보 조회 성공 roomId={}", roomId);
        return foundRoom.map(entityToResponseDtoConverter::convertRoom).orElse(null);
    }

    public List<JoinedRoomResponseDto> findJoinedRoomsByUid(Long uid) {
        List<Room> roomList = roomRepository.findJoinedRoomsByUid(uid);

        log.info("참여한 방 리스트 조회 성공 uid={}", uid);
        List<JoinedRoomResponseDto> myRoomsDto = new ArrayList<>();

        for (Room room : roomList) {
            String roomId = room.getId();
            ChatMessage message = chatMessageRepository.getLastMessage(roomId);

            LastMessage lastMessage = LastMessage.builder()
                    .messageId(message.getId())
                    .senderId(message.getSenderId())
                    .nickName(message.getNickName())
                    .senderProfileImage(message.getSenderProfileImage())
                    .content(message.getContent())
                    .createdAt(message.getCreatedAt())
                    .build();

            myRoomsDto.add(JoinedRoomResponseDto.builder()
                    .id(roomId)
                    .roomName(room.getRoomName())
                    .userCount(room.getUsers().size())
                    .users(room.getUsers())
                    .playlistOwner(entityToResponseDtoConverter.convertUser(room.getPlaylistOwner()))
                    .playlist(room.getPlaylist())
                    .thumbnailImage(room.getThumbnailImage())
                    .lastMessage(lastMessage)
                    .build());
        }

        return myRoomsDto;
    }

    public List<RoomResponseDto> findUnjoinedRooms(Long uid) {

        List<Room> unjoinedRooms = roomRepository.findUnjoinedRoomsSortedByCreationDate(uid, Sort.by(Sort.Direction.DESC, "createdAt"));

        log.info("참여하지 않은 방 리스트 조회 성공 uid={}", uid);

        return unjoinedRooms.stream()
                .map(room -> RoomResponseDto.builder()
                        .id(room.getId())
                        .roomName(room.getRoomName())
                        .thumbnailImage(room.getThumbnailImage())
                        .userCount(room.getUsers().size())
                        .users(room.getUsers().stream().map(entityToResponseDtoConverter::convertUser).collect(Collectors.toList()))
                        .playlist(room.getPlaylist())
                        .playlistOwner(entityToResponseDtoConverter.convertUser(room.getPlaylistOwner()))
                        .build())
                .collect(Collectors.toList());
    }


    public RoomResponseDto create(RoomCreateRequestDto roomCreateRequestDto) {
        // user build
        User owner = User.builder()
                .uid(roomCreateRequestDto.getUid())
                .nickName(roomCreateRequestDto.getNickName())
                .profileImage(roomCreateRequestDto.getUserProfileImage())
                .enteredAt(LocalDateTime.now())
                .build();

        List<User> users = new ArrayList<>();
        users.add(owner);

        // room build
        Room room = Room.builder()
                .roomName(roomCreateRequestDto.getPlaylist().getName())
                .playlist(roomCreateRequestDto.getPlaylist())
                .playlistDuration(roomCreateRequestDto.getPlaylist().getTotalPlaylistTime())
                .thumbnailImage(roomCreateRequestDto.getThumbnailImage())
                .playlistOwner(owner)
                .users(users)
                .build();

        log.info("생성된 방에 생성자 추가 성공 user={}", room.getUsers().toString());
        Room savedRoom = roomRepository.save(room);
        log.info("방 생성 성공 room={}", savedRoom);
        return entityToResponseDtoConverter.convertRoom(savedRoom);
    }

    public boolean exitRoom(String roomId, Long uid) {
        try {
            roomRepository.exitRoom(roomId, uid);
            log.info("방 나가기 성공 uid={}, roomId={}", uid, roomId);
            return true;
        } catch (Exception e) {
            log.error("방 나가기 실패 uid={}, roomId={}, : {}", uid, roomId, e.getMessage());
            return false;
        }
    }

    public CommonAPIMessage updateLastReadMsgId(ReadMessageUpdateRequestDto requestDto) {
        UpdateResult updateResult = roomRepository.updateLastReadMsgId(requestDto);
        if (updateResult.getModifiedCount() == 0)
            return new CommonAPIMessage(CommonAPIMessage.ResultEnum.failed, updateResult.getModifiedCount());
        return new CommonAPIMessage(CommonAPIMessage.ResultEnum.success, updateResult.getModifiedCount());
    }

    public boolean isExistingRoom(String roomId) {
        return roomRepository.existsById(roomId);
    }

    public List<RoomResponseDto> getAllRoomInfos() {
        return roomRepository.findAll().stream()
                .map(entityToResponseDtoConverter::convertRoom)
                .collect(Collectors.toList());
    }
}
