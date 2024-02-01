package chattingserver.service;

import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.dto.request.RoomCreateRequestDto;
import chattingserver.dto.response.RoomResponseDto;
import chattingserver.repository.RoomRepository;
import chattingserver.util.converter.EntityToResponseDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final EntityToResponseDtoConverter entityToResponseDtoConverter;

    public RoomResponseDto getRoomInfo(String roomId) {
        Optional<Room> foundRoom = roomRepository.findById(roomId);
        return foundRoom.map(entityToResponseDtoConverter::convertRoom).orElse(null);
    }

    public RoomResponseDto create(RoomCreateRequestDto roomCreateRequestDto) {
        // user build
        User user = User.builder()
                .uid(roomCreateRequestDto.getUid())
                .nickName(roomCreateRequestDto.getNickName())
                .enteredAt(LocalDateTime.now())
                .build();

        List<User> users = new ArrayList<>();
        users.add(user);

        log.info("user created={}", user);
        // room build
        Room room = Room.builder()
                .roomName(roomCreateRequestDto.getPlaylist().getName())
                .playlist(roomCreateRequestDto.getPlaylist())
                .users(users)
                .build();

        log.info("room created={}", room);

        log.info("room user added={}", room.getUsers().toString());
        Room savedRoom = roomRepository.save(room);
        log.info("saved room={}", savedRoom);
        return entityToResponseDtoConverter.convertRoom(savedRoom);
    }
}
