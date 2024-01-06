package spring.auth.dto.response;

import lombok.Builder;

@Builder
public record SignInResponse(
        String accessToken,
        String refreshToken
) {
}
