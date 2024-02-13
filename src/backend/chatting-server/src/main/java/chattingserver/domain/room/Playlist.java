package chattingserver.domain.room;

import java.util.List;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private String id;
    private String name;
    private List<Music> musics;
}
