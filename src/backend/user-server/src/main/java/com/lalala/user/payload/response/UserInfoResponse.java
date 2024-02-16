package com.lalala.user.payload.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String nickname,
        String profile
) {
}
