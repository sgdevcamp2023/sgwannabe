package spring.auth.common.exception;

import java.time.LocalDateTime;

public record SuccessObjectResponse(
        boolean success,
        int status,
        Object data,
        LocalDateTime timeStamp) {

    public SuccessObjectResponse(int status, Object data) {
        this(true, status, data, LocalDateTime.now());
    }

}

