package com.lalala.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalala.passport.HMACEncoder;
import com.lalala.passport.PassportGenerator;
import com.lalala.passport.PassportValidator;

@Configuration
@RequiredArgsConstructor
public class CommonModuleConfig {

    protected final ObjectMapper objectMapper;

    @Value("${passport.algorithm}")
    String HMacAlgorithm;

    @Value("${passport.key}")
    String passportSecretKey;

    @Bean
    public HMACEncoder hmacEncoder() {
        return new HMACEncoder(HMacAlgorithm, passportSecretKey);
    }

    @Bean
    public PassportGenerator passportGenerator() {
        return new PassportGenerator(objectMapper, hmacEncoder());
    }

    @Bean
    public PassportValidator passportValidator() {
        return new PassportValidator(objectMapper, hmacEncoder());
    }
}
