package com.lalala.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common Exception
    UNKNOWN_ERROR(400, "0001", "알 수 없는 오류가 발생했습니다."),
    INVALID_PARAMETER(400, "0002", "잘못된 매개 변수입니다."),
    METHOD_NOT_ALLOWED(405, "0003", "허용되지 않는 메소드입니다."),
    HANDLE_ACCESS_DENIED(403, "0004", "접근 권한이 없습니다."),
    DATABASE_ERROR(400, "0005", "데이터베이스가 응답하지 않습니다."),
    DATABASE_UPDATE_FAILED(400, "0006", "데이터베이스 업데이트에 실패했습니다."),

    // Global Passport Exception
    INVALID_PASSPORT(400, "0007", "패스포트 처리에 실패했습니다."),
    INVALID_PASSPORT_INTEGRITY(400, "0008", "패스포트 검증에 실패했습니다."),

    // 1,000 ~ 1,999 : Music / Music-Uploader
    TEMP_DOWNLOAD_FAILED(400, "1001", "임시 파일 다운로드에 실패했습니다."),
    MUSIC_UPLOAD_FAILED(400, "1002", "음원 업로드가 실패했습니다."),
    INVALID_MP3_TAG(400, "1003", "MP3 태그 버전을 찾을 수 없습니다."),
    MUSIC_NOT_FOUND(400, "1004", "음원을 조회할 수 없습니다."),
    ALBUM_NOT_FOUND(400, "1005", "앨범을 조회할 수 없습니다."),
    ARTIST_NOT_FOUND(400, "1006", "아티스트를 조회할 수 없습니다."),

    // 2,000 ~ 2,999 : Stroage / Streaming
    STORAGE_FILE_NOT_FOUND(400, "2000", "파일을 조회할 수 없습니다."),

    // 3,000 ~ 3,999 : Chart

    // 4,000 ~ 4,999 : Chatting

    // 5,000 ~ 5,999 : Gateway / Auth / User
    USER_NOT_FOUND(400, "5000", "유저를 조회할 수 없습니다."),
    EMAIL_NOT_FOUND(400, "5001", "이메일을 찾을 수 없습니다."),
    LOGIN_FAILED(400, "5002", "로그인에 실패했습니다."),
    DUPLICATE_EMAIL(400, "5003", "이메일이 중복됩니다."),
    SEND_MAIL_ERROR(400, "5004", "메일 전송 중 에러가 발생했습니다."),
    NOT_VALID_CODE(400, "5005", "유효한 코드가 아닙니다."),
    JWT_TOKEN_EXPIRED(400, "5006", "토큰이 만료됐습니다."),
    INVALID_TOKEN(400, "5007", "유효한 토큰이 아닙니다."),

    // 6,000 ~ 6,999 : PlayList / Feed
    PLAYLIST_NOT_FOUND(400, "6000", "해당하는 플레이리스트가 없습니다."),

// 7,000 ~ 7,999 : Search

// 8,000 ~ 8,999 : Alarm
;

    private final int status;
    private final String code;
    private final String message;
}
