package chattingserver.repository;

import chattingserver.domain.room.Room;
import chattingserver.domain.room.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, String>, RoomRepositoryCustom {
    Optional<Room> findById(String id);

    @Query(value = "{'users.uid': ?0}", fields = "{'id': 1, 'roomName': 1, 'thumbnailImage': 1, 'createdAt': 1}")
    List<Room> findJoinedRoomsByUid(Long uid, Sort sort, Pageable pageable);

    @Query(value = "{'users.uid': {$ne: ?0}, 'createdAt': {$lt: ?1}}", sort = "{'createdAt': -1}")
    List<Room> findUnjoinedRooms(Long uid, LocalDateTime lastCreatedAt, Pageable pageable);

    boolean existsByIdAndUsersUidNot(String roomId, Long uid);
}