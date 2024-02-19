package com.sgwannabe.playlistserver.global.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@Configuration
@EnableFeignClients("com.sgwannabe.playlistserver.external.feign")
public class OpenFeignConfig {

    private static final long START_PERIOD = 100L;
    private static final long MAX_PERIOD = 3_000L;
    private static final int MAX_ATTEMPTS = 5;

    @Bean
    Retryer.Default retryer() {
        return new Retryer.Default(START_PERIOD, MAX_PERIOD, MAX_ATTEMPTS);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        // Feign 은 debug 레벨로만 로그를 남깁니다.
        // loggin.level.com.lalala.gateway.external.feign=DEBUG
        return Logger.Level.FULL;
    }

    @Bean
    public FeignFormatterRegistrar dateTimeFormatterRegistrar() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }
}
