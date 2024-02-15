package chattingserver.dto.request;

import chattingserver.domain.room.Playlist;
import chattingserver.dto.response.UserListResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class IndexingRequestMessageDto {
    private String roomId;
    private String roomName;
    private String playlistId;
    private String thumbnailImage;
}