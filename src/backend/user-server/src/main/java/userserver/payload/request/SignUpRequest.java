package userserver.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotNull
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        String password
) {
}
