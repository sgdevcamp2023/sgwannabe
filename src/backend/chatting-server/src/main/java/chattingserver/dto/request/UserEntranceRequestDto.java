package chattingserver.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserEntranceRequestDto {

    private Long uid;
    private String nickName;
    private String profileImage;
}
