package chattingserver.domain.room;

import lombok.*;

import java.time.Duration;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private String id;
    private String name;

    private Long playlistOwnerId;
    private String playlistOwnerNickName;
    private String playlistOwnerProfileImage;

    private List<Music> musics;

    public Music getFirstMusic() {
        return musics.get(0);  // 무조건 있음 신뢰
    }

    public User getPlaylistOwner() {
        return User.builder()
                .uid(playlistOwnerId)
                .nickName(playlistOwnerNickName)
                .profileImage(playlistOwnerProfileImage)
                .build();
    }

    public Duration getTotalPlaylistTime() {
        return this.musics.stream()
                .map(music -> Duration.ofMinutes(Long.parseLong(music.getPlaytime().split(":")[0]))
                        .plus(Duration.ofSeconds(Long.parseLong(music.getPlaytime().split(":")[1]))))
                .reduce(Duration.ZERO, Duration::plus);
    }

}
