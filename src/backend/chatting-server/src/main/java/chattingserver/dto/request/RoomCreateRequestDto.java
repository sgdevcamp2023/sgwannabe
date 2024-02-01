package chattingserver.dto.request;

import chattingserver.domain.room.Playlist;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class RoomCreateRequestDto {

    private Long uid;
    private String nickName;
    private Playlist playlist;
}
