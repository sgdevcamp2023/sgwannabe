package com.lalala.music.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${uploader.url}")
    private String uploaderURL;

    @Bean(name = {"uploaderClient"})
    public RestClient restClient() {
        return RestClient.builder().baseUrl(uploaderURL).build();
    }
}
