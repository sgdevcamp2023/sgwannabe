package chattingserver.repository;

import chattingserver.domain.room.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room,String> {
    Optional<Room> findById(String id);

    @Override
    <S extends Room> S save(S entity);
}
