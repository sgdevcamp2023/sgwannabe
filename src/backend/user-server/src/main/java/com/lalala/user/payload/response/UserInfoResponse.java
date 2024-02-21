package com.lalala.user.payload.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String nickname,
        String profile
) {
}
