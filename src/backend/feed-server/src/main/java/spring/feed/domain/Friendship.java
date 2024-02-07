package spring.feed.domain;

import lombok.Builder;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.id.UuidStrategy;

import java.util.UUID;

@RelationshipEntity("IS_FOLLOWING")
@Builder
public class Friendship {
    @GeneratedValue(strategy = UuidStrategy.class)
    @Id
    private UUID id;

    @StartNode
    private User startNode;

    @EndNode
    private User endNode;
}
