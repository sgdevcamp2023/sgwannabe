package userserver.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum CustomUserCode {

    /**
     * 성공
     */
    SUCCESS_SIGNUP(HttpStatus.OK.value(), "100", "회원가입에 성공했습니다"),
    SUCCESS_CODE_CHECK(HttpStatus.OK.value(), "101", "인증코드가 일치합니다"),
    SUCCESS_MAIL_SEND(HttpStatus.OK.value(), "102", "메일 전송에 성공했습니다"),

    /**
     * 실패
     */
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST.value(), "200", "이메일을 입력해주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST.value(),  "201", "비밀번호를 입력해주세요"),
    EMPTY_NICKNAME(HttpStatus.BAD_REQUEST.value(), "202", "별명을 입력해주세요"),
    EMPTY_CODE(HttpStatus.BAD_REQUEST.value(),  "203", "인증코드를 입력해주세요"),

    INVALID_EMAIL(HttpStatus.BAD_REQUEST.value(),  "204", "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(),  "205", "비밀번호 형식이 올바르지 않습니다"),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST.value(),  "206", "이미 사용 중인 이메일입니다"),

    NOT_VALID_CODE(HttpStatus.BAD_REQUEST.value(), "207", "인증코드가 유효하지 않습니다"),
    SEND_EMAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "208", "메일 전송 중 에러가 발생했습니다"),

    FAIL_AUTH_FILTER(HttpStatus.UNAUTHORIZED.value(), "211", "Authentication 설정에 실패했습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "212", "만료된 토큰입니다"),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "213", "변형된 토큰입니다"),
    INVALID_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED.value(), "214", "토큰의 시그니쳐가 유효하지 않습니다"),
    NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED.value(), "215", "헤더에 토큰이 존재하지 않습니다"),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),  "210", "서버에 에러가 발생했습니다");


    private final Integer status;
    private final String code;
    private final String message;

}
