package spring.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAspect;
import com.lalala.config.CommonModuleConfig;

@SpringBootApplication
@Import({CommonModuleConfig.class, AuthenticationContext.class, PassportAspect.class})
public class FeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedApplication.class, args);
    }
}
