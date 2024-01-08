package spring.feed.dto;

public record FollowPayload(
        Long userId,
        String username,

        String email
) {
}
