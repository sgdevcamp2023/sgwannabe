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

    @Query("{'users.uid': ?0}")
    List<Room> findJoinedRoomsByUid(Long uid);

    @Query("{'users.uid': {$ne: ?0}}")
    List<Room> findUnjoinedRoomsSortedByCreationDate(Long uid, Sort sort);

    @Override
    <S extends Room> S save(S entity);

    @Override
    boolean existsById(String s);


}