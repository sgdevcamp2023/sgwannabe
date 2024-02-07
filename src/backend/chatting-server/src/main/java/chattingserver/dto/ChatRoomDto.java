package chattingserver.dto;

import chattingserver.domain.RoomState;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomDto {

    private String id;
    private String roomName;
    private RoomState roomState;
    private String createdAt;
    private String updatedAt;
}
