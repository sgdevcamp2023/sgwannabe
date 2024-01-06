package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;
import spring.auth.common.exception.request.ClientInvalidEmail;

public class UserNotFound extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserNotFound();

    private UserNotFound() {
        super(AuthErrorCode.USER_NOT_FOUND);
    }

}
