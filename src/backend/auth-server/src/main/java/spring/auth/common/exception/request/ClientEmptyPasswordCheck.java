package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientEmptyPasswordCheck extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyPasswordCheck();

    private ClientEmptyPasswordCheck() {
        super(AuthErrorCode.CLIENT_EMPTY_PASSWORD_CHECK);
    }
}
