package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class InvalidToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new InvalidToken();

    private InvalidToken() {
        super(AuthErrorCode.INVALID_TOKEN);
    }
}
