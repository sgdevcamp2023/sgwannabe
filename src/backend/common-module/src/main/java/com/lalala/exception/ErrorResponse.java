package com.lalala.exception;

import lombok.Getter;

import com.lalala.response.BaseResponse;

@Getter
public class ErrorResponse extends BaseResponse<Object> {

    private ErrorResponse(final ErrorCode code) {
        super(code.getStatus(), code.getMessage(), code.getCode(), null);
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
