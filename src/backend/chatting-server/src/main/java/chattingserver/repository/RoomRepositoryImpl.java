package chattingserver.repository;

import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import chattingserver.dto.request.ReadMessageUpdateRequestDto;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Repository
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public RoomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void exitRoom(String roomId, Long uid) {
        Query query = new Query(Criteria.where("_id").is(roomId).and("users.uid").is(uid));
        Update update = new Update().pull("users", new Query(Criteria.where("uid").is(uid)));
        mongoTemplate.updateFirst(query, update, Room.class);
    }

    @Override
    public UpdateResult updateLastReadMsgId(ReadMessageUpdateRequestDto requestDto) {

        Query query = Query.query(
                Criteria.where("_id").is(requestDto.getRoomId())
                        .andOperator(Criteria.where("users").elemMatch(Criteria.where("uid").is(requestDto.getUid())))
        );

        Update update = new Update().set("users.$.lastReadMessageId", requestDto.getMessageId());
        return mongoTemplate.updateFirst(query, update, Room.class);
    }

    @Override
    public UpdateResult addUserToRoom(String roomId, User user) {
        Query query = new Query(Criteria.where("_id").is(roomId));
        Update update = new Update().addToSet("users", user);
        return mongoTemplate.updateFirst(query, update, Room.class);
    }
}