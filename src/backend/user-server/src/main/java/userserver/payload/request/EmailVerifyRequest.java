package userserver.payload.request;

import jakarta.validation.constraints.NotNull;

public record EmailVerifyRequest(
        @NotNull
        String email,
        @NotNull
        String code
) {
}
