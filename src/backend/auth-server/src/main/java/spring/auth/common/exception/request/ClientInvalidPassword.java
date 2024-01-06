package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientInvalidPassword extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientInvalidPassword();

    private ClientInvalidPassword() {
        super(AuthErrorCode.CLIENT_INVALID_PASSWORD);
    }

}
