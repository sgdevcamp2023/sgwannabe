package com.sgwannabe.playlistserver.external.feign;

import com.sgwannabe.playlistserver.external.feign.dto.MusicRoomDummyDto;
import com.sgwannabe.playlistserver.music.domain.Music;

public class MusicToMusicDummyDtoConverter {

    public MusicRoomDummyDto convert(Music music) {
        return MusicRoomDummyDto.builder()
                .id(music.getId())
                .title(music.getTitle())
                .artist(music.getArtist())
                .playtime(music.getPlaytime())
                .thumbnail(music.getThumbnail())
                .build();
    }
}
