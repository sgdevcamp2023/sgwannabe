package com.lalala.aop;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.lalala.exception.BusinessException;
import com.lalala.exception.ErrorCode;
import com.lalala.passport.component.UserInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@RequiredArgsConstructor
public class OnlyAdminAspect {

    @Around("@annotation(com.lalala.aop.OnlyAdmin)")
    public Object validateAdminFromUserInfo(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        final UserInfo userInfo = AuthenticationContext.CONTEXT.get().userInfo();

        if (userInfo == null) {
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        if (!userInfo.isAdmin()) {
            throw new BusinessException(ErrorCode.HANDLE_ACCESS_DENIED);
        }

        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }
}
