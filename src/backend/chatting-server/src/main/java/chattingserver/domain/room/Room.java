package chattingserver.domain.room;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Document(collection = "rooms")
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private String id;
    private String roomName;
    private Playlist playlist;
    private Duration playlistDuration;

    private String thumbnailImage;

    private List<User> users;
    private LocalDateTime createdAt;

    private User playlistOwner;
    public void setMembers(List<User> users) {
        this.users = users;
    }



}
