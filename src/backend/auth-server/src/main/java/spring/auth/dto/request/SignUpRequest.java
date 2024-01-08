package spring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import spring.auth.domain.ERole;
import spring.auth.domain.User;

import java.util.Set;


@Schema(description = "User SignUp Request")
public record SignUpRequest(
        @NotBlank
        @Schema(description = "username", example="신짱구")        
        String username,

        @NotBlank
        @Schema(description = "email", example="jjangu@gmail.com")
        @Email
        String email,

        @NotBlank @Length(min=8, max=20)
        @Schema(description = "password", example="gggggggggg")
        String password

) {
        public User toEntity(String hashPassword) {
                return User.builder()
                        .username(username)
                        .email(email)
                        .password(hashPassword)
                        .build();
        }
}
