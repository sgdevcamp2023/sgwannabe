package com.lalala.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BaseResponse<T> {
    private static final String SUCCESS_CODE = "0000";

    protected int status;
    protected String code;
    protected String message;

    private T data;

    public static <T> BaseResponse<T> from(int status, String message, T data) {
        return new BaseResponse<>(status, SUCCESS_CODE, message, data);
    }
}
