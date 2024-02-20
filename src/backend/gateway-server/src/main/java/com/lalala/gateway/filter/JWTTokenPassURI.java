package com.lalala.gateway.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JWTTokenPassURI {
    SEARCH("/search"),
    ;

    final String uri;
}
