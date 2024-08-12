package chattingserver.repository;

import chattingserver.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<ChatMessage> findByRoomIdWithPagingAndFiltering(String roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Query query = new Query(Criteria.where("roomId").is(roomId))
                .with(pageable);

        List<ChatMessage> messages = mongoTemplate.find(query, ChatMessage.class);

        return PageableExecutionUtils.getPage(
                messages,
                pageable,
                () -> mongoTemplate.count(Query.query(Criteria.where("roomId").is(roomId)), ChatMessage.class)
        );
    }

    @Override
    public List<ChatMessage> getAllMessagesAtRoom(String roomId) {
        return mongoTemplate.find(
                Query.query(Criteria.where("roomId").is(roomId))
                        .with(Sort.by(Sort.Direction.DESC, "createdAt")),
                ChatMessage.class
        );
    }

    @Override
    public List<ChatMessage> getNewMessages(String roomId, String readMsgId) {
        ChatMessage lastReadMessage = mongoTemplate.findById(new ObjectId(readMsgId), ChatMessage.class);
        if (lastReadMessage == null) {
            return Collections.emptyList();
        }

        return mongoTemplate.find(
                Query.query(Criteria.where("roomId").is(roomId)
                                .and("createdAt").gt(lastReadMessage.getCreatedAt()))
                        .with(Sort.by(Sort.Direction.DESC, "createdAt")),
                ChatMessage.class
        );
    }

    @Override
    public List<ChatMessage> findPreviousMessages(String roomId, String readMsgId, int limit) {
        return mongoTemplate.find(
                Query.query(Criteria.where("roomId").is(roomId)
                                .and("_id").lt(new ObjectId(readMsgId)))
                        .limit(limit)
                        .with(Sort.by(Sort.Direction.DESC, "createdAt")),
                ChatMessage.class
        );
    }

    @Override
    public List<ChatMessage> findMessagesNoOffset(String roomId, LocalDateTime lastMessageTime, int limit) {
        Criteria criteria = Criteria.where("roomId").is(roomId);
        if (lastMessageTime != null) {
            criteria.and("createdAt").lt(lastMessageTime);
        }

        Query query = Query.query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .limit(limit);

        return mongoTemplate.find(query, ChatMessage.class);
    }

}
