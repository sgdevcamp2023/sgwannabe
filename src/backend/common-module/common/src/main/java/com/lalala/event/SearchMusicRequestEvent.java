package com.lalala.event;

import java.time.LocalDateTime;

public record SearchMusicRequestEvent(
        Long id,
        String title,
        Short playTime,
        Long artistId,
        String artist,
        String artistGender,
        String artistType,
        String artistAgency,
        Long albumId,
        String album,
        String albumType,
        LocalDateTime albumReleasedAt)
        implements LalalaEvent {
    @Override
    public String getTopic() {
        return TopicConstant.SEARCH_MUSIC_REQUEST.value;
    }
}
