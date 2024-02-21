package com.lalala.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final Long MAX_AGE = 3000L;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("127.0.0.1:3000")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(MAX_AGE);
    }
}
