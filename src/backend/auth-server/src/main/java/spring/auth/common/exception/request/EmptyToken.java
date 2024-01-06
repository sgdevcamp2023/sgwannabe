package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class EmptyToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new EmptyToken();

    private EmptyToken() {
        super(AuthErrorCode.EMPTY_TOKEN);
    }
}
