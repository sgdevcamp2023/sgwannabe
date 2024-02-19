package com.lalala.feed;

import com.lalala.aop.AuthenticationContext;
import com.lalala.mvc.aop.PassportAspect;
import com.lalala.mvc.config.CommonMvcModuleConfig;
import com.lalala.mvc.exception.GlobalExceptionHandler;
import com.lalala.mvc.response.BaseResponseBodyAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        CommonMvcModuleConfig.class,
        AuthenticationContext.class,
        PassportAspect.class,
        BaseResponseBodyAdvice.class,
        GlobalExceptionHandler.class,
})
public class FeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedApplication.class, args);
    }
}
