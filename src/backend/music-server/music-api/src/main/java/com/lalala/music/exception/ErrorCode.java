package com.lalala.music.exception;

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

    // 1000 ~ Music Exception
    TEMP_DOWNLOAD_FAILED(HttpStatus.BAD_REQUEST, "1001", "임시 파일 다운로드에 실패했습니다."),
    MUSIC_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "1002", "음원 업로드가 실패했습니다."),
    INVALID_MP3_TAG(HttpStatus.BAD_REQUEST, "1003", "MP3 태그 버전을 찾을 수 없습니다."),
    MUSIC_NOT_FOUND(HttpStatus.BAD_REQUEST, "1004", "음원을 조회할 수 없습니다."),
    ALBUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "1005", "앨범을 조회할 수 없습니다."),
    ARTIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "1006", "아티스트를 조회할 수 없습니다."),
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
