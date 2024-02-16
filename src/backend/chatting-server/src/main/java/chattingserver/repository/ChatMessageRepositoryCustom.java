package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface ChatMessageRepositoryCustom {

    Page<ChatMessage> findByRoomIdWithPagingAndFiltering(String roomId, int page, int size);

    Collection<ChatMessage> getAllMessagesAtRoom(String roomId);

    List<ChatMessage> getNewMessages(String roomId, String readMsgId);

    ChatMessage getLastMessage(String roomId);
    List<ChatMessage> findPreviousMessages(String roomId, String readMsgId, int limit);

}
