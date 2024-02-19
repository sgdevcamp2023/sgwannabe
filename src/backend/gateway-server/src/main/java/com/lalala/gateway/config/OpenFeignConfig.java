package com.lalala.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lalala.gateway.external.feign.FeignAuthClient;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.reactive.ReactorDecoder;
import feign.reactive.ReactorFeign;

@Configuration
public class OpenFeignConfig {

    @Value("${auth.url}")
    private String authEndPoint;

    @Bean
    public FeignAuthClient authClient() {
        return ReactorFeign.builder()
                .logLevel(Level.FULL)
                .encoder(new GsonEncoder())
                .decoder(new ReactorDecoder(new GsonDecoder()))
                .target(FeignAuthClient.class, authEndPoint);
    }
}
