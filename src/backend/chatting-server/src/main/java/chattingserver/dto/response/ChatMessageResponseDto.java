package chattingserver.dto.response;

import java.time.LocalDateTime;

import lombok.*;

import chattingserver.util.constant.MessageType;

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
