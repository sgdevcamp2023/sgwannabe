package chattingserver.dto.response;

import chattingserver.domain.chat.LastMessage;
import chattingserver.domain.room.Playlist;
import chattingserver.domain.room.User;
import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinedRoomResponseDto {
    private String id;
    private String roomName;
    private long userCount;
    private List<User> users;
    private UserListResponseDto playlistOwner;
    private Playlist playlist;
    private String thumbnailImage;
    private LastMessage lastMessage;
}
