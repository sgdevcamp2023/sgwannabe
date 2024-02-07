package spring.feed.dto;

public record FollowPayload(
        String userId,
        String nickname,
        String email
) {
}
