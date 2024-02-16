package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>, ChatMessageRepositoryCustom {


    ChatMessage getLastMessage(String roomId);
}
