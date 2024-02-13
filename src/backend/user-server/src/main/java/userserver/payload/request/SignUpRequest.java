package userserver.payload.request;

import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Builder
public record SignUpRequest(
        @NotBlank(message = "닉네임을 입력해주세요") String nickname,
        @NotBlank(message = "이메일을 입력해주세요") @Email String email,
        @NotBlank(message = "비밀번호를 입력해주세요") @Size(max = 68) String password) {}
