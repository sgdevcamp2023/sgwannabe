package com.lalala.music.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.lalala.music.entity.AlbumEntity;
import com.lalala.music.entity.ArtistEntity;
import com.lalala.music.entity.MusicEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MusicDTO {
    private final Long id;
    private final String title;
    private final Short playTime;
    private final Integer likeCount;
    private final AlbumDTO album;
    private final FileDTO file;
    private final ArtistDTO artist;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MusicDTO from(MusicEntity music, AlbumEntity album, ArtistEntity artist) {
        return new MusicDTO(
                music.getId(),
                music.getTitle(),
                music.getPlayTime(),
                music.getLikeCount(),
                AlbumDTO.from(album, artist),
                FileDTO.from(music.getFile()),
                ArtistDTO.from(artist),
                music.getCreatedAt(),
                music.getUpdatedAt());
    }
}
