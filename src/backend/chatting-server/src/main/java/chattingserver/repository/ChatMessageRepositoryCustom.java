package chattingserver.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import chattingserver.domain.chat.ChatMessage;

public interface ChatMessageRepositoryCustom {

    Page<ChatMessage> findByRoomIdWithPagingAndFiltering(String roomId, int page, int size);

    Collection<ChatMessage> getAllMessagesAtRoom(String roomId);

    List<ChatMessage> getNewMessages(String roomId, String readMsgId);
}
