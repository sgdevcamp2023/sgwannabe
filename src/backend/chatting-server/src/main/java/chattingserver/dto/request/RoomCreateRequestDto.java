package chattingserver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import chattingserver.domain.room.Playlist;
import jakarta.validation.constraints.NotBlank;

@Getter
@ToString
@Builder
public class RoomCreateRequestDto {

    @NotBlank private Long uid;
    @NotBlank private String nickName;
    @NotBlank private Playlist playlist;
}
