package chattingserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import chattingserver.domain.chat.ChatMessage;

public interface ChatMessageRepository
        extends MongoRepository<ChatMessage, String>, ChatMessageRepositoryCustom {}
