package chattingserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserEntranceRequestDto {

    @NotBlank
    private Long uid;
    @NotBlank
    private String nickName;
    private String profileImage;
}
