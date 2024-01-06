package spring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import spring.auth.domain.User;

public record SignInRequest(
        @NotBlank @Email
        @Schema(description = "email", example="jjangu@gmail.com")
        String email,
        @NotBlank @Length(min=8, max=20)
        @Schema(description = "password", example="gggggggggg")
        String password
) {
        public User toEntity() {
                return User.builder()
                        .email(email)
                        .password(password)
                        .build();
        }
}
