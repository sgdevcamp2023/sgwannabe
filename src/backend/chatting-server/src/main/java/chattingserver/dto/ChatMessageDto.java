package chattingserver.dto;

import java.time.LocalDateTime;

import lombok.*;

import chattingserver.util.constant.MessageType;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String id;
    @NotBlank
    private MessageType messageType;
    @NotBlank
    private String roomId;
    @NotBlank
    private Long senderId;
    @NotBlank
    private String nickName;

    private String senderProfileImage;
    @NotBlank
    private String content;
    private LocalDateTime createdAt;
}
