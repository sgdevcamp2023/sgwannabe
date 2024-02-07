package com.lalala.music.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {
    private static final long MB_UNIT = 1024L * 1024L;

    @Value("${file.multipart.maxUploadSizeMB:20}")
    private long maxUploadSizeMB;

    @Value("${file.multipart.maxUploadSizePerFileMB:20}")
    private long maxUploadSizePerFileMB;

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxRequestSize(DataSize.ofBytes(maxUploadSizeMB * MB_UNIT));
        factory.setMaxFileSize(DataSize.ofBytes(maxUploadSizePerFileMB * MB_UNIT));
        return factory.createMultipartConfig();
    }
}
