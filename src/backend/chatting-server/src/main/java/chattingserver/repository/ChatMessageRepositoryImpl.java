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

@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom{

    private final MongoTemplate mongoTemplate;


    @Override
    public Page<ChatMessage> findByRoomIdWithPagingAndFiltering(String roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Query query = new Query()
                .with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());
        query.addCriteria(Criteria.where("roomId").is(roomId));

        List<ChatMessage> messages = mongoTemplate.find(query, ChatMessage.class);

        return PageableExecutionUtils.getPage(
                messages,
                pageable,
                ()-> mongoTemplate.count(query.skip(-1).limit(-1), ChatMessage.class)
        );
    }

    @Override
    public Collection<ChatMessage> getAllMessagesAtRoom(String roomId) {
        Query query = Query.query(Criteria.where("roomId").is(roomId)).with(
                Sort.by(Sort.Direction.DESC,"createdAt"));
        return mongoTemplate.find(query, ChatMessage.class);
    }

    @Override
    public List<ChatMessage> getNewMessages(String roomId, String readMsgId) {
        ObjectId mObjId = new ObjectId(readMsgId);
        LocalDateTime createAt = mongoTemplate.findOne(Query.query(Criteria.where("id").is(mObjId)), ChatMessage.class).getCreatedAt();

        return mongoTemplate.find(
                Query.query(Criteria.where("roomId").is(roomId))
                        .addCriteria(Criteria.where("createdAt").gt(createAt)).with(
                                Sort.by(Sort.Direction.DESC,"createdAt"))
                , ChatMessage.class);
    }

    @Override
    public ChatMessage getLastMessage(String roomId) {

        Query query = Query.query(Criteria.where("roomId").is(roomId)).with(Sort.by(Sort.Direction.DESC,"createdAt"));

        query.fields().exclude("roomId");
        query.fields().exclude("type");

        ChatMessage chatMessage = mongoTemplate.findOne(query, ChatMessage.class);
        if(chatMessage == null){
            return new ChatMessage();
        }
        return chatMessage;
    }

    @Override
    public List<ChatMessage> findPreviousMessages(String roomId, String readMsgId, int limit) {
        ObjectId readMsgObjectId = new ObjectId(readMsgId);
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId).and("_id").lt(readMsgObjectId));
        query.limit(20); // 최대 20개의 메시지 가져오도록 제한 설정
        return mongoTemplate.find(query, ChatMessage.class);
    }
    //TODO error처리 이전 메시지 못가져오고 빈배열 반환되는중 lt가 문제임
}
