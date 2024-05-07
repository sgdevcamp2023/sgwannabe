package com.lalala.music.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Music {
    private final Long id;
    private final String title;
    private final short playTime;
    private final Album album;
    private final File file;
    private final Artist artist;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
