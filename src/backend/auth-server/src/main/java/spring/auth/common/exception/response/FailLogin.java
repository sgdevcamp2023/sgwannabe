package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;
import spring.auth.common.exception.request.ClientInvalidEmail;

public class FailLogin extends GlobalCustomException {

    public static final GlobalCustomException EXCEPTION = new FailLogin();

    private FailLogin() {
        super(AuthErrorCode.FAIL_LOGIN);
    }


}
