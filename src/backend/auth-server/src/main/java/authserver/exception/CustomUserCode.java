package authserver.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static authserver.common.UserStatic.*;

@Getter
@RequiredArgsConstructor
public enum CustomUserCode {

    /**
     * 성공
     */
    SUCCESS_SIGNIN(SUCCESS, "100", "로그인에 성공했습니다"),
    SUCCESS_SIGNOUT(SUCCESS, "102", "로그아웃에 성공했습니다"),

    /**
     * 실패
     */
    EMPTY_EMAIL(BAD_REQUEST, "200", "이메일을 입력해주세요"),
    EMPTY_PASSWORD(BAD_REQUEST, "201", "비밀번호를 입력해주세요"),

    INVALID_EMAIL(BAD_REQUEST, "204", "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD(BAD_REQUEST, "205", "비밀번호 형식이 올바르지 않습니다"),


    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "210", "서버에 에러가 발생했습니다");


    private final Integer status;
    private final String code;
    private final String message;

}
