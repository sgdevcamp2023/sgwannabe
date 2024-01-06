package spring.auth.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;

@Slf4j
public class ExceptionStatusProvider {

    public static void throwError(Errors errors) {
        String code = errors.getFieldError().getCode();
        String field = errors.getFieldError().getField();
        throw new GlobalCustomException(ExceptionStatusProvider.getExceptionStatus(code, field));
    }

    public static AuthErrorCode getExceptionStatus(String code, String field) {
        switch (code) {
            case "NotBlank" -> {
                switch (field) {
                    case "email" -> {
                        return AuthErrorCode.CLIENT_EMPTY_EMAIL;
                    }
                    case "password" -> {
                        return AuthErrorCode.CLIENT_EMPTY_PASSWORD;
                    }
                    case "passwordCheck" -> {
                        return AuthErrorCode.CLIENT_EMPTY_PASSWORD_CHECK;
                    }
                    case "username" -> {
                        return AuthErrorCode.CLIENT_EMPTY_USERNAME;
                    }
                }
            }
            case "Length" -> { // "Password Length
                return AuthErrorCode.CLIENT_INVALID_PASSWORD;
            }
            case "Email" ->{
                return AuthErrorCode.CLIENT_INVALID_EMAIL;
            }
        }
        return AuthErrorCode.RESPONSE_ERROR;
    }
}