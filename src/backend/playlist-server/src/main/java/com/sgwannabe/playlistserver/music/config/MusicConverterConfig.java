package com.sgwannabe.playlistserver.music.config;

import com.sgwannabe.playlistserver.music.util.MusicToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicConverterConfig {

    @Bean
    public MusicToDtoConverter musicToDtoConverter() {
        return new MusicToDtoConverter();
    }
}