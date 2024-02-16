package chattingserver.config.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chattingserver.util.converter.EntityToResponseDtoConverter;

@Configuration
public class ConverterConfig {

    @Bean
    public EntityToResponseDtoConverter entityToResponseDtoConverter() {
        return new EntityToResponseDtoConverter();
    }
}
