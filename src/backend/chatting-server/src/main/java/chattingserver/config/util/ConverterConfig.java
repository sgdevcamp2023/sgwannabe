package chattingserver.config.util;

import chattingserver.util.converter.EntityToResponseDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public EntityToResponseDtoConverter entityToResponseDtoConverter() {
        return new EntityToResponseDtoConverter();
    }
}
