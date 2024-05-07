package com.lalala.music.mapper;

import com.lalala.music.domain.*;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;
import java.util.List;

public class AlbumDetailMapper {
    public static AlbumDetail from(
            AlbumEntity album, ArtistEntity artist, List<MusicEntity> musics) {
        return new AlbumDetail(
                album.getId(),
                album.getType(),
                album.getTitle(),
                album.getCoverUrl(),
                album.getReleasedAt(),
                ArtistMapper.from(artist),
                musics.stream().map(music -> MusicMapper.from(music, album, artist)).toList());
    }

    public static AlbumDetail from(AlbumEntity album, ArtistEntity artist) {
        return from(album, artist, List.of());
    }
}
