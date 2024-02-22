package com.sgwannabe.playlistserver.playlist.config;

import com.sgwannabe.playlistserver.external.feign.MusicToMusicDummyDtoConverter;
import com.sgwannabe.playlistserver.external.feign.PlaylistToPlaylistDummyDtoConverter;
import com.sgwannabe.playlistserver.playlist.util.PlaylistToDtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public PlaylistToDtoConverter playlistToDtoConverter() {
        return new PlaylistToDtoConverter();
    }

    @Bean
    public PlaylistToPlaylistDummyDtoConverter playlistToPlaylistDummyDtoConverter() {
        return new PlaylistToPlaylistDummyDtoConverter(musicToMusicDummyDtoConverter());
    }

    @Bean
    public MusicToMusicDummyDtoConverter musicToMusicDummyDtoConverter() {
        return new MusicToMusicDummyDtoConverter();
    }
}
