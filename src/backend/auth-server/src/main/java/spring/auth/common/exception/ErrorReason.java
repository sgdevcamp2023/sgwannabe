package spring.auth.common.exception;

import lombok.Builder;

@Builder
public record ErrorReason(
        Integer status,
        String code,
        String reason
) {
}
