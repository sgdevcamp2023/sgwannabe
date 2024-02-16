package chattingserver.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "공통 에러 응답 스키마")
@Data
@RequiredArgsConstructor
@Builder
public class CommonAPIError {
    private final int status;
    private final String errorCode;
    private final String description;
    private final String errorMsg;
    private final LocalDateTime timestamp;
}
