package spring.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserAndTokenResponse(
        @Schema(description = "access token")
        String accessToken,

        @Schema(description = "refresh token")
        String refreshToken,

        String username

) {
}
