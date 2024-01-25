package com.sgwannabe.playlistserver.playlist.config;

import com.sgwannabe.playlistserver.playlist.dto.PlaylistToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public PlaylistToDtoConverter playlistToDtoConverter() {
        return new PlaylistToDtoConverter();
    }
}
