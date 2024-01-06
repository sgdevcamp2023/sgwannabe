package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class IncorrectPasswordCheck extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new IncorrectPasswordCheck();

    private IncorrectPasswordCheck() {
        super(AuthErrorCode.INCORRECT_PASSWORD_CHECK);
    }
}
