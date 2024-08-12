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
        Query query = new Query(Criteria.where("_id").is(roomId));
        Update update = new Update().pull("users", Query.query(Criteria.where("uid").is(uid)));
        mongoTemplate.updateFirst(query, update, Room.class);
    }

    @Override
    public UpdateResult updateLastReadMsgId(String roomId, Long uid, String messageId) {
        Query query = new Query(Criteria.where("_id").is(roomId).and("users.uid").is(uid));
        Update update = new Update().set("users.$.lastReadMessageId", messageId);
        return mongoTemplate.updateFirst(query, update, Room.class);
    }

    @Override
    public UpdateResult addUserToRoom(String roomId, User user) {
        Query query = new Query(Criteria.where("_id").is(roomId));
        Update update = new Update().addToSet("users", user);
        return mongoTemplate.updateFirst(query, update, Room.class);
    }

    @Override
    public void updatePlaylist(String roomId, Playlist playlist) {
        Query query = new Query(Criteria.where("_id").is(roomId));
        Update update = new Update()
                .set("playlist", playlist)
                .set("playlistDuration", playlist.getTotalPlaylistTime())
                .set("thumbnailImage", playlist.getFirstMusic().getThumbnail());
        mongoTemplate.updateFirst(query, update, Room.class);
    }

    public List<Room> findRoomsNoOffset(Long uid, LocalDateTime lastCreatedAt, int limit, boolean joined) {
        Criteria criteria = new Criteria();
        if (joined) {
            criteria.and("users.uid").is(uid);
        } else {
            criteria.and("users.uid").ne(uid);
        }

        if (lastCreatedAt != null) {
            criteria.and("createdAt").lt(lastCreatedAt);
        }

        Query query = Query.query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .limit(limit);

        return mongoTemplate.find(query, Room.class);
    }
}