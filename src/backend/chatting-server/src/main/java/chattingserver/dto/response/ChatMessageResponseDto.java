package chattingserver.dto.response;

import chattingserver.util.constant.MessageType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {

    private String id;
    private MessageType messageType;
    private String roomId;
    private Long senderId;
    private String nickName;
    private String senderProfileImage;
    private String content;
    private LocalDateTime createdAt;
}
