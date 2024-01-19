package spring.feed.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import spring.feed.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Neo4jRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<User> findByNickname(String nickname);

    @Query(value="MATCH (a:User)-->(b:User) WHERE a.userId = $userId RETURN b")
    List<User> findFollowing(String userId);

    @Query(value="MATCH (a:User)<--(b:User) WHERE a.userId = $userId return b")
    List<User> findFollowers(String userId);

    @Query(value="MATCH (a:User{userId:$fromUserId}), (b:User{userId:$toUserId}) RETURN EXISTS((a)-[:IS_FOLLOWING]->(b))")
    boolean isFollowing(String fromUserId, String toUserId);

    @Query(value="MATCH (a:User)-[r:IS_FOLLOWING]->(b:User) WHERE a.userId = $fromUserId AND b.userId = $toUserId DELETE r")
    void stopFollowing(String fromUserId, String toUserId);
}