package chattingserver.domain.room;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class Playlist {
    private String id;
    private String name;
    private List<Music> musics;
}
