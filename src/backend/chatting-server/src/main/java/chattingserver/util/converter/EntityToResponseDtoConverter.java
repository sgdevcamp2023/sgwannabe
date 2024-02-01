package chattingserver.util.converter;

import chattingserver.domain.room.Room;
import chattingserver.dto.response.RoomResponseDto;

public class EntityToResponseDtoConverter {

    public RoomResponseDto convertRoom(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .userCount(room.getUsers().size())
                .users(room.getUsers())
                .playlist(room.getPlaylist())
//                .thumbnail(room.getPlaylist().getMusics().get(0)) // 플레이리스트에 곡 무조건 존재함을 신뢰
                .build();
    }
}
