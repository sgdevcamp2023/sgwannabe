package spring.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserAndTokenResponse(
        @Schema(description = "유저 아이디")
        Long id,
        @Schema(description = "유저 이름")
        String username,
        @Schema(description = "access token")
        String accessToken,
        @Schema(description = "refresh token")
        String refreshToken


) {
}
