package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class ClientUnauthorizedException extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new ClientUnauthorizedException();

    private ClientUnauthorizedException() {
        super(AuthErrorCode.CLIENT_UNAUTHORIZED);
    }

}
