package com.lalala.dto.event;

import com.lalala.dto.LalalaEvent;

public record StreamingCompleteEvent(Long userId, Long musicId) implements LalalaEvent {
    @Override
    public String getTopic() {
        return TopicConstant.STREAMING_COMPLETE.value;
    }
}
