package chattingserver.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

import chattingserver.domain.room.Playlist;
import chattingserver.domain.room.User;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private String id;
    private String roomName;
    private long userCount;
    private List<User> users;
    private Playlist playlist;
    private LocalDateTime createdAt;
    //    private Music thumbnail;

}
