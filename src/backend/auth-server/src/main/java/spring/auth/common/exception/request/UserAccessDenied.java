package spring.auth.common.exception.request;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class UserAccessDenied extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new UserAccessDenied();

    private UserAccessDenied() {
        super(AuthErrorCode.USER_ACCESS_DENIED);
    }

}
