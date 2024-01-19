package chattingserver.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageDto {

    private String roomId;
    private String senderId;
    private String senderName;
    private String content;
    private String createdAt;
}
