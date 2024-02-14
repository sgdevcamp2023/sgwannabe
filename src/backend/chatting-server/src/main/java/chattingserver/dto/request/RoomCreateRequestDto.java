package chattingserver.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import chattingserver.domain.room.Playlist;
import jakarta.validation.constraints.NotBlank;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomCreateRequestDto {

    @NotBlank private Long uid;
    @NotBlank private String nickName;
    @NotBlank private Playlist playlist;
}
