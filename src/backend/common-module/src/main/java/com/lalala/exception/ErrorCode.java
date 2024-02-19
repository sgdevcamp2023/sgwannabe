package com.lalala.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common Exception
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "0001", "알 수 없는 오류가 발생했습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "0002", "잘못된 매개 변수입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "0003", "허용되지 않는 메소드입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "0004", "접근 권한이 없습니다."),
    DATABASE_ERROR(HttpStatus.BAD_REQUEST, "0005", "데이터베이스가 응답하지 않습니다."),
    DATABASE_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "0006", "데이터베이스 업데이트에 실패했습니다."),

    // Global Passport Exception
    INVALID_PASSPORT(HttpStatus.BAD_REQUEST, "0007", "패스포트 처리에 실패했습니다."),
    INVALID_PASSPORT_INTEGRITY(HttpStatus.BAD_REQUEST, "0008", "패스포트 검증에 실패했습니다."),

    // 1,000 ~ 1,999 : Music / Music-Uploader
    TEMP_DOWNLOAD_FAILED(HttpStatus.BAD_REQUEST, "1001", "임시 파일 다운로드에 실패했습니다."),
    MUSIC_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "1002", "음원 업로드가 실패했습니다."),
    INVALID_MP3_TAG(HttpStatus.BAD_REQUEST, "1003", "MP3 태그 버전을 찾을 수 없습니다."),
    MUSIC_NOT_FOUND(HttpStatus.BAD_REQUEST, "1004", "음원을 조회할 수 없습니다."),
    ALBUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "1005", "앨범을 조회할 수 없습니다."),
    ARTIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "1006", "아티스트를 조회할 수 없습니다."),

    // 2,000 ~ 2,999 : Stroage / Streaming

    // 3,000 ~ 3,999 : Chart

    // 4,000 ~ 4,999 : Chatting

    // 5,000 ~ 5,999 : Gateway / Auth / User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "5000", "유저를 조회할 수 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "5001", "이메일을 찾을 수 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "5002", "로그인에 실패했습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "5003", "이메일이 중복됩니다."),
    SEND_MAIL_ERROR(HttpStatus.BAD_REQUEST, "5004", "메일 전송 중 에러가 발생했습니다."),
    NOT_VALID_CODE(HttpStatus.BAD_REQUEST, "5005", "유효한 코드가 아닙니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "5005", "유효한 토큰이 아닙니다."),


// 6,000 ~ 6,999 : PlayList / Feed

// 7,000 ~ 7,999 : Search

// 8,000 ~ 8,999 : Alarm
;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status.value();
        this.message = message;
        this.code = code;
    }
}
