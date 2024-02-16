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
}
