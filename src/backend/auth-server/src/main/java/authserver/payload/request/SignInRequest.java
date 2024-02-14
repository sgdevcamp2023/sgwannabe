package authserver.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(

        @NotBlank(message = "이메일을 입력해주세요")
        @Email
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(max=68)
        String password
) {
}
