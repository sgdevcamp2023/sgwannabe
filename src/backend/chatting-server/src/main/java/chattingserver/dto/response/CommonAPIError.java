package chattingserver.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

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
