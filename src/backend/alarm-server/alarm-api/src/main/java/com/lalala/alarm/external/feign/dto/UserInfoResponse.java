package com.lalala.alarm.external.feign.dto;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String nickname,
        String profile
) {
}

