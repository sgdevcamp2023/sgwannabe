package chattingserver.dto;

import chattingserver.util.constant.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ChatMessageDto {

    private String id;
    private MessageType messageType;
    private String roomId;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
}
