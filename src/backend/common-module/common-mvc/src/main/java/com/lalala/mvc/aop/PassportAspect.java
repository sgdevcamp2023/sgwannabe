package com.lalala.mvc.aop;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.lalala.aop.AuthenticationContext;
import com.lalala.mvc.passport.PassportExtractor;
import com.lalala.passport.component.Passport;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@RequiredArgsConstructor
public class PassportAspect {

    private final HttpServletRequest httpServletRequest;
    private final PassportExtractor passportExtractor;

    @Around("@annotation(com.lalala.aop.PassportAuthentication)")
    public Object setUserInfoByServlet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final Passport passport = passportExtractor.getPassportFromRequestHeader(httpServletRequest);
        AuthenticationContext.CONTEXT.set(passport);

        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
