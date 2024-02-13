package chattingserver.dto.response;

import java.util.List;

import lombok.*;

import chattingserver.domain.room.User;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinedRoomResponseDto {
    private String roomId;
    private String roomName;
    private List<User> users;

    //    private LastMessage last_message;
}
