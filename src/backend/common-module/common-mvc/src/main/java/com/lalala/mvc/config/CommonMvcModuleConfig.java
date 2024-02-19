package com.lalala.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.config.CommonModuleConfig;
import com.lalala.mvc.passport.PassportExtractor;

@Configuration
public class CommonMvcModuleConfig extends CommonModuleConfig {

    public CommonMvcModuleConfig(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Bean
    public PassportExtractor passportExtractor() {
        return new PassportExtractor(objectMapper, passportValidator());
    }
}
