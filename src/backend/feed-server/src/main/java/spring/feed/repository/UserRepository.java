package spring.feed.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import spring.feed.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Neo4jRepository<User, UUID> {

    Optional<User> findByUserId(Long userId);
    Optional<User> findByUsername(String username);

    @Query(value="MATCH (a:User)-->(b:User) WHERE a.userId = $userId RETURN b")
    List<User> findFollowing(Long userId);
}