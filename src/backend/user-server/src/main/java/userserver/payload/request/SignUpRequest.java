package userserver.payload.request;

import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String email,
        String password
) {
}
