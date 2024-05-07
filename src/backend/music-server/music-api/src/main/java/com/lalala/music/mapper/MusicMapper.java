package com.lalala.music.mapper;

import com.lalala.music.domain.Music;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicMapper {
    public static Music from(MusicEntity music, AlbumEntity album, ArtistEntity artist) {
        return new Music(
                music.getId(),
                music.getTitle(),
                music.getPlayTime(),
                AlbumMapper.from(album, artist),
                MusicFileMapper.from(music.getFile()),
                ArtistMapper.from(artist),
                music.getCreatedAt(),
                music.getUpdatedAt());
    }
}
