package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientEmptyEmail extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyEmail();

    private ClientEmptyEmail() {
        super(AuthErrorCode.CLIENT_EMPTY_EMAIL);
    }
}
