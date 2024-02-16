package chattingserver.domain.room;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long uid;
    private String nickName;
    private String profileImage;
    private String lastReadMessageId;
    private LocalDateTime enteredAt;
}
