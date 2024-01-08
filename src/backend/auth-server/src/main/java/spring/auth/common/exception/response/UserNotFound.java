package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class UserNotFound extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserNotFound();

    private UserNotFound() {
        super(AuthErrorCode.USER_NOT_FOUND);
    }

}
