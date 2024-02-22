package chattingserver.dto.request;

import chattingserver.domain.room.Playlist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
public class RoomCreateRequestDto {

    @NotBlank
    private Long uid;
    @NotBlank
    private String nickName;
    @NotNull
    private String userProfileImage;
    @NotBlank
    private Playlist playlist;

    public String getThumbnailImage() {
        return this.playlist.getFirstMusic().getThumbnail();
    }
}
