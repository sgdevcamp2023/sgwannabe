package spring.auth.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;
import spring.auth.domain.FollowUser;

public interface FollowRepository extends ReactiveNeo4jRepository<FollowUser, String> {
    Mono<FollowUser> findOneByEmail(String email);
}