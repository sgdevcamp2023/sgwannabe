package com.lalala.music.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SuccessResponse<T> {
    private int status;
    private String code;
    private String message;
    private T data;

    public static <T> SuccessResponse<T> from(HttpStatus status, String message, T data) {
        return new SuccessResponse<>(
                status.value(),
                "0000", // 성공 코드
                message,
                data);
    }
}
