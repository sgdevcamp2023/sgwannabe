package com.lalala.event;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TopicConstant {
    STREAMING_COMPLETE("streaming_complete"),
    SEARCH_MUSIC_REQUEST("search_music_request"),
    NOTIFICATION_REQUEST("notification_request"),
    ;

    final String value;
}
