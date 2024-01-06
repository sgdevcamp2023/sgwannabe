package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ExpiredToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ExpiredToken();

    private ExpiredToken() {
        super(AuthErrorCode.EXPIRED_TOKEN);
    }
}
