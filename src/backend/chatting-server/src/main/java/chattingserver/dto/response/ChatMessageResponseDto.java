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
    private String content;
    private Boolean isLeader;
    private String profile;
    private LocalDateTime createdAt;
}
