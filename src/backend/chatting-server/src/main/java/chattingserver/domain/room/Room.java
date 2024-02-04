package chattingserver.domain.room;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "rooms")
@Builder
@Getter
@ToString
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private String id;
    private String roomName;
    private Playlist playlist;

    private List<User> users;
    private LocalDateTime createdAt;
    public void setMembers(List<User> users) {
        this.users = users;
    }

}
