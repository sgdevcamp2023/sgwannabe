package userserver.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailAuthCodeRequest(
        @NotNull
        @Email
        String email
) {
}
