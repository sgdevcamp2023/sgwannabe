package spring.feed.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.id.UuidStrategy;

import java.util.Set;
import java.util.UUID;

@NodeEntity(label="User")
@Builder
@Getter @Setter
public class User {

    @GeneratedValue(strategy = UuidStrategy.class)
    @Id
    private UUID id;

    private Long userId;

    private String email;

    private String username;

    @Relationship(type="IS_FOLLOWING")
    private Set<Friendship> friendships;
}
