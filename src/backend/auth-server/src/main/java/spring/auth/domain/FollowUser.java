package spring.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Node("FollowUser")
@AllArgsConstructor
public class FollowUser {
    @Id
    private String email;
    private String username;

}