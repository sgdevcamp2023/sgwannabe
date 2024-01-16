package authserver.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum CustomUserCode {

    /**
     * 성공
     */
    SUCCESS_SIGNIN(HttpStatus.OK.value(), "100", "로그인에 성공했습니다"),
    SUCCESS_SIGNOUT(HttpStatus.OK.value(), "102", "로그아웃에 성공했습니다"),

    /**
     * 실패
     */
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST.value(), "200", "이메일을 입력해주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST.value(), "201", "비밀번호를 입력해주세요"),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST.value(), "204", "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "205", "비밀번호 형식이 올바르지 않습니다"),

    FAIL_LOGIN(HttpStatus.BAD_REQUEST.value(), "206", "이메일 또는 비밀번호를 확인해주세요"),

    FAIL_AUTH_FILTER(HttpStatus.UNAUTHORIZED.value(), "211", "Authentication 설정에 실패했습니다"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "210", "서버에 에러가 발생했습니다");


    private final int status;
    private final String code;
    private final String message;

}
