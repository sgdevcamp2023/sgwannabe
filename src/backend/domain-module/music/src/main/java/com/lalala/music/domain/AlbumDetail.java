package com.lalala.music.domain;

import java.time.LocalDateTime;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AlbumDetail {
    private final Long id;
    private final AlbumType type;
    private final String title;
    private final String coverUrl;
    private final LocalDateTime releasedAt;

    private final Artist artist;
    private final List<Music> musics;
}
