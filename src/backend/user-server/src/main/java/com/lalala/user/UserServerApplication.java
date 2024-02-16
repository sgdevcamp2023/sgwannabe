package com.lalala.user;

import com.lalala.exception.GlobalExceptionHandler;
import com.lalala.response.BaseResponseBodyAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAspect;
import com.lalala.config.CommonModuleConfig;

@Import({
        CommonModuleConfig.class,
        AuthenticationContext.class,
        PassportAspect.class,
        BaseResponseBodyAdvice.class,
        GlobalExceptionHandler.class
})
@SpringBootApplication
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }
}
