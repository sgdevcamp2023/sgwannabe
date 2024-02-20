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
public class MusicDetailDTO {
    private final Long id;
    private final String title;
    private final Short playTime;
    private final Integer likeCount;
    private final String lyrics;
    private final AlbumDTO album;
    private final FileDTO file;
    private final ParticipantsDetailDTO participants;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static MusicDetailDTO from(MusicEntity music, ArtistEntity artist, AlbumEntity album) {
        return new MusicDetailDTO(
                music.getId(),
                music.getTitle(),
                music.getPlayTime(),
                music.getLikeCount(),
                music.getLyrics(),
                AlbumDTO.from(album, artist),
                FileDTO.from(music.getFile()),
                ParticipantsDetailDTO.from(artist),
                music.getCreatedAt(),
                music.getUpdatedAt());
    }
}
