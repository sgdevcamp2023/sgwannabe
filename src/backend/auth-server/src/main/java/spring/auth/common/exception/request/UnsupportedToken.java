package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class UnsupportedToken extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UnsupportedToken();

    private UnsupportedToken() {
        super(AuthErrorCode.UNSUPPORTED_TOKEN);
    }
}
