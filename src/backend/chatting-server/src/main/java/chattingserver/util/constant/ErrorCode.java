package chattingserver.util.constant;

import java.time.LocalDateTime;

import lombok.Getter;

import chattingserver.dto.response.CommonAPIError;

@Getter
public enum ErrorCode {
    ROOM_NOT_FOUND_ERROR(4003, "CHATTING-001", "id에 해당하는 채팅방이 없습니다.");

    private final int status;
    private final String code;
    private final String description;

    ErrorCode(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public CommonAPIError toErrorResponseDto(String msg) {
        return CommonAPIError.builder()
                .status(this.status)
                .errorCode(this.code)
                .description(this.description)
                .errorMsg(msg)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
