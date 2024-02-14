package authserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class CustomUserProvider {

    public static void throwError(Errors errors) {
        String code = errors.getFieldError().getCode();
        String field = errors.getFieldError().getField();

        throw new CustomException(CustomUserProvider.getExceptionCode(code, field));
    }


    public static CustomUserCode getExceptionCode(String code, String target) {
        switch (code) {
            case "NotBlank" -> {
                switch (target) {
                    case "email" -> {
                        return CustomUserCode.EMPTY_EMAIL;
                    }
                    case "password" -> {
                        return CustomUserCode.EMPTY_PASSWORD;
                    }
                }
            }
            case "Email" ->{
                return CustomUserCode.INVALID_EMAIL;
            }
            case "Pattern", "Size" -> {
                return CustomUserCode.INVALID_PASSWORD;
            }
        }

        return CustomUserCode.INTERNAL_SERVER_ERROR;

    }

}
