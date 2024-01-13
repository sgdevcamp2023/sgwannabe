package userserver.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailVerifyRequest(
        @NotNull
        @Email
        String email,
        @NotNull
        String code
) {
}
