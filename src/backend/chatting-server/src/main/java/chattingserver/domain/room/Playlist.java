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
        return musics.isEmpty() ? null : musics.get(0);
    }

    public User getPlaylistOwner() {
        return User.builder()
                .uid(playlistOwnerId)
                .nickName(playlistOwnerNickName)
                .profileImage(playlistOwnerProfileImage)
                .build();
    }

    public Duration getTotalPlaylistTime() {
        return musics.stream()
                .map(Music::getPlayTimeDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

}
