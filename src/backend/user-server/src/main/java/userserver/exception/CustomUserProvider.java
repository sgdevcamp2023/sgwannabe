package userserver.exception;

import org.springframework.validation.Errors;

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
                    case "nickname" -> {
                        return CustomUserCode.EMPTY_NICKNAME;
                    }
                }
            }
            case "Pattern", "Size" -> {
                switch (target) {
                    case "email" -> {
                        return CustomUserCode.INVALID_EMAIL;
                    }
                    case "password" -> {
                        return CustomUserCode.INVALID_PASSWORD;
                    }
                }
            }
        }

        return CustomUserCode.INTERNAL_SERVER_ERROR;
    }
}
