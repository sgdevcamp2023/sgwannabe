package com.sgwannabe.playlistserver.external.feign.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
