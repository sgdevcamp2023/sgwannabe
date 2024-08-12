package chattingserver.domain.room;

import lombok.*;

import java.time.Duration;
import java.util.List;

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

    @Value
    public static class PlayTimeDuration {
        Duration minutes;
        Duration seconds;

        public static PlayTimeDuration from(List<String> parsedPlaytime) {
            return new PlayTimeDuration(
                    Duration.ofMinutes(Long.parseLong(parsedPlaytime.get(0))),
                    Duration.ofSeconds(Long.parseLong(parsedPlaytime.get(1)))
            );
        }

        public Duration getPlayTimeDuration() {
            return minutes.plus(seconds);
        }
    }

    private static class PlayTimeParser {
        public static List<String> parse(String playtime) {
            return List.of(playtime.split(":"));
        }
    }

}
