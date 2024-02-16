package chattingserver.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UserListResponseDto {
    private Long uid;
    private String nickName;
    private String profileImage;
}