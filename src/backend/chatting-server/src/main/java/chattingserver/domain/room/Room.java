package chattingserver.domain.room;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "rooms")
@Builder
@Getter
@ToString
@Slf4j
public class Room {

    @Id
    private String id;
    private String roomName;
    private Playlist playlist;

//    @Indexed
    private List<User> users;
    public void setMembers(List<User> users) {
        this.users = users;
    }

}
