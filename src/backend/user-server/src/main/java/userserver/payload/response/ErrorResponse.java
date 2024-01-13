package userserver.payload.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        boolean success,
        int status,
        String code,
        String reason,
        LocalDateTime date_time,
        String path
) {

    public ErrorResponse(int status,String code, String reason, String path) {
        this(false, status, code, reason, LocalDateTime.now(), path);
    }
}
