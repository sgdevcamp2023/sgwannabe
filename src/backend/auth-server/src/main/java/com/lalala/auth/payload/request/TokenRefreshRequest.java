package com.lalala.auth.payload.request;

public record TokenRefreshRequest(
        String accessToken,
        String refreshToken
) {
}
