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

    static class PlayTimeDuration {
        private final Duration minutes;
        private final Duration seconds;

        private PlayTimeDuration(
                final Long minutes,
                final Long seconds
        ) {
            this.minutes = Duration.ofMinutes(minutes);
            this.seconds = Duration.ofSeconds(seconds);
        }

        public Duration getPlayTimeDuration() {
            return minutes.plus(seconds);
        }

        public static PlayTimeDuration from(final List<String> parsedPlaytime) {
            return new PlayTimeDuration(
                    Long.parseLong(parsedPlaytime.get(0)),
                    Long.parseLong(parsedPlaytime.get(1))
            );
        }
    }

    static class PlayTimeParser {
        private PlayTimeParser() {}

        public static List<String> parse(String playtime) {
            return List.of(
                    playtime.split(":")[0],
                    playtime.split(":")[1]
            );
        }
    }

    public Duration getPlayTimeDuration() {
        final List<String> parsedPlaytime = PlayTimeParser.parse(playtime);
        final PlayTimeDuration playTimeDuration = PlayTimeDuration.from(parsedPlaytime);
        return playTimeDuration.getPlayTimeDuration();
    }
}
