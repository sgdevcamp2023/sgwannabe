package spring.auth.common.exception.docs;

import spring.auth.common.annotation.ExceptionDoc;
import spring.auth.common.annotation.ExplainError;
import spring.auth.common.annotation.SwaggerException;
import spring.auth.common.exception.GlobalCustomException;
import spring.auth.common.exception.request.*;
import spring.auth.common.exception.response.DuplicateEmail;

@ExceptionDoc
public class SignUpExceptionDocs implements SwaggerException {

    /**
     * request error
     */
    @ExplainError("이름을 입력하지 않은 경우")
    public GlobalCustomException 이름_미입력 = ClientEmptyUsername.EXCEPTION;
    @ExplainError("이메일을 입력하지 않은 경우")
    public GlobalCustomException 이메일_미입력 = ClientEmptyEmail.EXCEPTION;

    @ExplainError("비밀번호를 입력하지 않은 경우")
    public GlobalCustomException 비밀번호_미입력 = ClientEmptyPassword.EXCEPTION;



    /**
     * response error
     */
    @ExplainError("이미 사용중인 이메일인 경우")
    public GlobalCustomException 이메일_중복 = DuplicateEmail.EXCEPTION;

}
