package chattingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.lalala.aop.AuthenticationContext;
import com.lalala.aop.PassportAspect;
import com.lalala.config.CommonModuleConfig;
import com.lalala.config.KafkaConsumerConfig;
import com.lalala.config.KafkaProducerConfig;

@Import({
        CommonModuleConfig.class,
        AuthenticationContext.class,
        PassportAspect.class,
})
@SpringBootApplication
public class ChattingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChattingApplication.class, args);
    }
}
