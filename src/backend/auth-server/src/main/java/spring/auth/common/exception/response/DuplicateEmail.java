package spring.auth.common.exception.response;

import spring.auth.common.exception.AuthErrorCode;
import spring.auth.common.exception.GlobalCustomException;

public class DuplicateEmail extends GlobalCustomException {
    public static final GlobalCustomException EXCEPTION = new DuplicateEmail();

    private DuplicateEmail() {
        super(AuthErrorCode.DUPLICATE_EMAIL);
    }
}
