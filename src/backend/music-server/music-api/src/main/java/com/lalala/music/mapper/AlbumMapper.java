package com.lalala.music.mapper;

import com.lalala.music.domain.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumMapper {
    public static Album from(AlbumEntity album, ArtistEntity artist) {
        return new Album(
                album.getId(),
                album.getType(),
                album.getTitle(),
                album.getCoverUrl(),
                album.getReleasedAt(),
                ArtistMapper.from(artist));
    }
}
