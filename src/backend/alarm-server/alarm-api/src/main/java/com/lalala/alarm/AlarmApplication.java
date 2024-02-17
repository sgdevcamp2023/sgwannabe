package com.lalala.alarm;

import com.lalala.aop.AuthenticationContext;
import com.lalala.config.KafkaConsumerConfig;
import com.lalala.config.KafkaProducerConfig;
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
		KafkaConsumerConfig.class,
		KafkaProducerConfig.class,
		BaseResponseBodyAdvice.class,
		GlobalExceptionHandler.class,
})
@SpringBootApplication
public class AlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlarmApplication.class, args);
	}

}
