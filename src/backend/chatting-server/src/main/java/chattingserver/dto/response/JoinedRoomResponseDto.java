package chattingserver.dto.response;

import chattingserver.domain.room.User;
import lombok.*;
import java.util.List;

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
