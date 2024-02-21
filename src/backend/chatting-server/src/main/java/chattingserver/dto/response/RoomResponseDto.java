package chattingserver.dto.response;

import chattingserver.domain.room.Playlist;
import chattingserver.domain.room.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {
    private String id;
    private String roomName;
    private long userCount;
    private List<UserListResponseDto> users;
    private UserListResponseDto playlistOwner;
    private long musicCount;
    private Playlist playlist;
    private String thumbnailImage;
    private LocalDateTime createdAt;

}