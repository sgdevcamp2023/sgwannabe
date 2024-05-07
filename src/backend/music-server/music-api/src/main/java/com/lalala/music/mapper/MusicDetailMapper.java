package com.lalala.music.mapper;

import com.lalala.music.domain.MusicDetail;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;

public class MusicDetailMapper {
    public static MusicDetail from(MusicEntity music, ArtistEntity artist, AlbumEntity album) {
        return new MusicDetail(
                music.getId(),
                music.getTitle(),
                music.getPlayTime(),
                music.getLyrics(),
                AlbumMapper.from(album, artist),
                MusicFileMapper.from(music.getFile()),
                ParticipantsDetailMapper.from(artist),
                music.getCreatedAt(),
                music.getUpdatedAt());
    }
}
