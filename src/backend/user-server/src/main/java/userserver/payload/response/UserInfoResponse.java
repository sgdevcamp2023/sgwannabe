package userserver.payload.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String username,
        String email

) {
}