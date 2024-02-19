package com.lalala.event;

public record SearchMusicRequestEvent(
        Long id, String title, Short playTime, Long artistId, String artist, Long albumId, String album)
        implements LalalaEvent {
    @Override
    public String getTopic() {
        return TopicConstant.SEARCH_MUSIC_REQUEST.value;
    }
}
