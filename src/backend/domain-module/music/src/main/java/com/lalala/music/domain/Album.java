package com.lalala.music.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Album {
    private final Long id;
    private final AlbumType type;
    private final String title;
    private final String coverUrl;
    private final LocalDateTime releasedAt;

    private final Artist artist;
}
