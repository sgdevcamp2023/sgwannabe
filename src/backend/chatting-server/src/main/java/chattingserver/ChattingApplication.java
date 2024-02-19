package chattingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.lalala.aop.AuthenticationContext;
import com.lalala.mvc.aop.PassportAspect;
import com.lalala.mvc.config.CommonMvcModuleConfig;


@Import({
		CommonMvcModuleConfig.class,
		AuthenticationContext.class,
		PassportAspect.class,
})
@SpringBootApplication
public class ChattingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattingApplication.class, args);
	}
}
