package spring.auth.common.exception.docs;

import spring.auth.common.annotation.ExceptionDoc;
import spring.auth.common.annotation.ExplainError;
import spring.auth.common.annotation.SwaggerException;
import spring.auth.common.exception.GlobalCustomException;
import spring.auth.common.exception.request.ClientEmptyEmail;
import spring.auth.common.exception.request.ClientEmptyPassword;
import spring.auth.common.exception.response.FailLogin;

@ExceptionDoc
public class SignInExceptionDocs implements SwaggerException {

    /**
     * request error
     */
    @ExplainError("이메일을 입력하지 않은 경우")
    public GlobalCustomException 이메일_미입력 = ClientEmptyEmail.EXCEPTION;

    @ExplainError("비밀번호를 입력하지 않은 경우")
    public GlobalCustomException 비밀번호_미입력 = ClientEmptyPassword.EXCEPTION;
    @ExplainError("이메일 또는 비밀번호가 잘못된 경우")
    public GlobalCustomException 로그인_실패 = FailLogin.EXCEPTION;




}
