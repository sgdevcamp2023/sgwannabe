package com.sgwannabe.playlistserver.music.util;

import com.sgwannabe.playlistserver.music.domain.Music;
import com.sgwannabe.playlistserver.music.dto.MusicResponseDto;

public class MusicToDtoConverter {
    public MusicResponseDto convert(Music music) {

        return MusicResponseDto.builder()
                .id(music.getId())
                .title(music.getTitle())
                .artistId(music.getArtistId())
                .artist(music.getArtist())
                .albumId(music.getAlbumId())
                .album(music.getAlbum())
                .thumbnail(music.getThumbnail())
                .playtime(music.getPlaytime())
                .build();
    }
}
