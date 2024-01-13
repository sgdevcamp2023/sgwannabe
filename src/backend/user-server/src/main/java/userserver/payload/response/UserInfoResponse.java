package userserver.payload.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String nickname,
        String email

) {
}