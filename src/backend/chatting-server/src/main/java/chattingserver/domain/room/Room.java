package chattingserver.domain.room;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id private String id;
    private String roomName;
    private Playlist playlist;

    private List<User> users;
    private LocalDateTime createdAt;

    public void setMembers(List<User> users) {
        this.users = users;
    }
}
