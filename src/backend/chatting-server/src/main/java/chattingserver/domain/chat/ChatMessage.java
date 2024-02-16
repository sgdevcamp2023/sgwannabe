package chattingserver.domain.chat;

import chattingserver.util.constant.MessageType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    private String id;
    private MessageType messageType;
    private String roomId;
    private Long senderId;
    private String nickName;
    private String senderProfileImage;
    private String content;
    private LocalDateTime createdAt;

}
