package com.sgwannabe.playlistserver.global.config;

import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignResponseDecoderConfig {
    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    @Bean
    SpringFormEncoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    SpringDecoder feignFormDecoder() {
        return new SpringDecoder(messageConverters);
    }
}

