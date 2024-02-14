package com.lalala.auth.payload.response;

import lombok.Builder;

@Builder
public record UserAndTokenResponse(
        Long id, String nickname, String access_token, String refresh_token) {}
