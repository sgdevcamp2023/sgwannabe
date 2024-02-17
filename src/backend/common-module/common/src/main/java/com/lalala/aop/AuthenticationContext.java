package com.lalala.aop;

import com.lalala.passport.component.Passport;
import com.lalala.passport.component.UserInfo;

public class AuthenticationContext {
    public static final ThreadLocal<Passport> CONTEXT = new ThreadLocal<>();

    private AuthenticationContext() {}

    public static UserInfo getUserInfo() {
        return CONTEXT.get().userInfo();
    }

    public static String getIntegrityKey() {
        return CONTEXT.get().integrityKey();
    }
}
