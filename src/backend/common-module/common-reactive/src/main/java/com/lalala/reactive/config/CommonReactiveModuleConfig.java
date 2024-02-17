package com.lalala.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.config.CommonModuleConfig;
import com.lalala.reactive.passport.PassportExtractor;

@Configuration
public class CommonReactiveModuleConfig extends CommonModuleConfig {

    public CommonReactiveModuleConfig(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Bean
    public PassportExtractor passportExtractor() {
        return new PassportExtractor(objectMapper, passportValidator());
    }
}
