package com.sgwannabe.playlistserver.playlist.config;

import com.sgwannabe.playlistserver.playlist.util.PlaylistToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaylistConverterConfig {

    @Bean
    public PlaylistToDtoConverter playlistToDtoConverter() {
        return new PlaylistToDtoConverter();
    }
}
