package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientEmptyUsername extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientEmptyUsername();

    public ClientEmptyUsername() {
        super(AuthErrorCode.CLIENT_EMPTY_USERNAME);
    }
}
