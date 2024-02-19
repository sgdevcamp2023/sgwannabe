package com.sgwannabe.playlistserver.external.feign.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumDTO {
    private final Long id;
    private final AlbumType type;
    private final String title;
    private final String coverUrl;
    private final LocalDateTime releasedAt;

    private final ArtistDTO artist;
}
