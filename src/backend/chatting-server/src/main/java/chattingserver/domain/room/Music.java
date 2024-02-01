package chattingserver.domain.room;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Music {
    private String title;
    private String artist;
    private String playtime;
    private String thumbnail;
}
