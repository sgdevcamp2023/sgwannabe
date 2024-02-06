package userserver.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(max=68)
        String password
) {
}
