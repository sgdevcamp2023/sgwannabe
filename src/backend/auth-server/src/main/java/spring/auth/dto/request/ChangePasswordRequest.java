package spring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordRequest(
        @NotBlank @Length(min=8, max=20)
        @Schema(description = "password", example="gggggggggg")
        String password,

        @NotBlank
        @Schema(description = "passwordCheck", example="gggggggggg")
        String passwordCheck
) { }
