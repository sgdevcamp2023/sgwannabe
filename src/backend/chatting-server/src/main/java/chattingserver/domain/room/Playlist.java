package chattingserver.domain.room;

import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private String id;
    private String name;
    private List<Music> musics;

    public Music getFirstMusic() {
        return musics.stream().findFirst().get();  // 무조건 있음 신뢰
    }
}
