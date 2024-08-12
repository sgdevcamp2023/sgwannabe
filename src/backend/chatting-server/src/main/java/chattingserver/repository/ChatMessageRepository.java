package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>, ChatMessageRepositoryCustom {
    @Query(value = "{'roomId': ?0}", sort = "{'createdAt': -1}", fields = "{'_id': 0}", limit = 1)
    ChatMessage getLastMessage(String roomId);
}
