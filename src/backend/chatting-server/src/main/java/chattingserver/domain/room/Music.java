package chattingserver.domain.room;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    private Long id;
    private String title;
    private String artist;
    private String playtime;
    private String thumbnail;
}
