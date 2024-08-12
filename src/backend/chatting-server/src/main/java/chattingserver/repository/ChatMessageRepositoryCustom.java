package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface ChatMessageRepositoryCustom {
    List<ChatMessage> findMessagesNoOffset(String roomId, LocalDateTime lastMessageTime, int limit);
    List<ChatMessage> getAllMessagesAtRoom(String roomId);
    List<ChatMessage> getNewMessages(String roomId, String readMsgId);
    List<ChatMessage> findPreviousMessages(String roomId, String readMsgId, int limit);
}