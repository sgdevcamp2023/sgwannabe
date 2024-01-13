package userserver.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static userserver.common.UserStatic.*;

@Getter
@RequiredArgsConstructor
public enum CustomUserCode {

    /**
     * 성공
     */
    SUCCESS_SIGNUP(SUCCESS, "100", "회원가입에 성공했습니다"),
    SUCCESS_CODE_CHECK(SUCCESS, "101", "인증코드가 일치합니다"),
    SUCCESS_MAIL_SEND(SUCCESS, "102", "메일 전송에 성공했습니다"),

    /**
     * 실패
     */
    EMPTY_EMAIL(BAD_REQUEST, "200", "이메일을 입력해주세요"),
    EMPTY_PASSWORD(BAD_REQUEST, "201", "비밀번호를 입력해주세요"),
    EMPTY_NICKNAME(BAD_REQUEST, "202", "별명을 입력해주세요"),
    EMPTY_CODE(BAD_REQUEST, "203", "인증코드를 입력해주세요"),

    INVALID_EMAIL(BAD_REQUEST, "204", "이메일 형식이 올바르지 않습니다"),
    INVALID_PASSWORD(BAD_REQUEST, "205", "비밀번호 형식이 올바르지 않습니다"),

    DUPLICATE_EMAIL(BAD_REQUEST, "206", "이미 사용 중인 이메일입니다"),

    NOT_VALID_CODE(BAD_REQUEST,"207", "인증코드가 유효하지 않습니다"),
    SEND_EMAIL_ERROR(INTERNAL_SERVER, "208", "메일 전송 중 에러가 발생했습니다"),
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "210", "서버에 에러가 발생했습니다");


    private final Integer status;
    private final String code;
    private final String message;

}
