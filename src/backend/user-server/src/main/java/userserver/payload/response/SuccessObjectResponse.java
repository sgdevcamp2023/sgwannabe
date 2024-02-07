package userserver.payload.response;
import java.time.LocalDateTime;
public record SuccessObjectResponse(
        boolean success,
        int status,
        Object data,
        LocalDateTime date_time
) {
    public SuccessObjectResponse(int status, Object data) {
        this(true, status, data, LocalDateTime.now());
    }
}
