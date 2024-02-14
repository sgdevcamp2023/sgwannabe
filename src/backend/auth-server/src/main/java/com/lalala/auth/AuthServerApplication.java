package com.lalala.auth;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAspect;
import com.lalala.config.CommonModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        CommonModuleConfig.class,
        AuthenticationContext.class,
        PassportAspect.class,
})
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
