package chattingserver.domain.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LastMessage {
    private String messageId;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
}
