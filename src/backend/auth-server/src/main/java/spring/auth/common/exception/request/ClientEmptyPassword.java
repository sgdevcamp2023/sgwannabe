package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientEmptyPassword extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyPassword();

    public ClientEmptyPassword() {
        super(AuthErrorCode.CLIENT_EMPTY_PASSWORD);
    }
}
