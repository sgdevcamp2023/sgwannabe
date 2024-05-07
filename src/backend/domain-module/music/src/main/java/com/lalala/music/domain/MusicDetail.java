package com.lalala.music.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MusicDetail {
    private final Long id;
    private final String title;
    private final Short playTime;
    private final String lyrics;
    private final Album album;
    private final File file;
    private final ParticipantsDetail participants;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
