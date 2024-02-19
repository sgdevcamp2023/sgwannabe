package com.lalala.auth;

import com.lalala.aop.AuthenticationContext;
import com.lalala.mvc.aop.PassportAspect;
import com.lalala.mvc.config.CommonMvcModuleConfig;
import com.lalala.mvc.exception.GlobalExceptionHandler;
import com.lalala.mvc.response.BaseResponseBodyAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        CommonMvcModuleConfig.class,
        AuthenticationContext.class,
        PassportAspect.class,
        BaseResponseBodyAdvice.class,
        GlobalExceptionHandler.class,
})
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
