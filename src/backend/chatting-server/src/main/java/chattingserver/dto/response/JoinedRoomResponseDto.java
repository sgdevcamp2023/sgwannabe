package chattingserver.dto.response;

import chattingserver.domain.chat.LastMessage;
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
    private long userCount;
    private String thumbnailImage;
    private List<User> users;
    private UserListResponseDto playlistOwner;
    private LastMessage lastMessage;
}
