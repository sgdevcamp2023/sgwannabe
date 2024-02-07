package spring.feed.domain;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity(label = "User")
@Builder
@Data
public class User {

    @GeneratedValue
    @Id
    private Long id;

    private String userId;

    private String email;

    private String nickname;

    @Relationship(type = "IS_FOLLOWING")
    private Set<Friendship> friendships;

}
