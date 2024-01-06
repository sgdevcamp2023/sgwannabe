package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientInvalidEmail extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientInvalidEmail();

    private ClientInvalidEmail() {
        super(AuthErrorCode.CLIENT_INVALID_EMAIL);
    }
}
