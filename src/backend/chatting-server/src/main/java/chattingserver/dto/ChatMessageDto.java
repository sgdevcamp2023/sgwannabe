package chattingserver.dto;

import chattingserver.util.constant.MessageType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long currentMusicId;
}
