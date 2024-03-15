package com.lalala.event;

public record StreamingCompleteEvent(Long musicId) implements LalalaEvent {
    @Override
    public String getTopic() {
        return TopicConstant.STREAMING_COMPLETE.value;
    }
}
