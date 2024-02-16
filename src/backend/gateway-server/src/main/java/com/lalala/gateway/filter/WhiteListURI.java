package com.lalala.gateway.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    AUTH_SIGN_IN("/auth/signin"),
    AUTH_PASSPORT_VALIDATE("/auth/passport"),
    USER_EMAIL("/user/users/email"),
    USER_VERIFICATION("/user/users/verification"),
    USER_SIGN_UP("/user/users/signup"),
    STREAMING("/streaming/streaming");

    final String uri;
}
