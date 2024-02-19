package com.lalala.reactive.aop;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.lalala.aop.AuthenticationContext;
import com.lalala.passport.component.Passport;
import com.lalala.reactive.passport.PassportExtractor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@RequiredArgsConstructor
public class PassportAspect {

    private final ServerRequest request;
    private final PassportExtractor passportExtractor;

    @Around("@annotation(com.lalala.aop.PassportAuthentication)")
    public Object setUserInfoByServlet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Passport passport = passportExtractor.getPassportFromRequestHeader(request);
        AuthenticationContext.CONTEXT.set(passport);

        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
