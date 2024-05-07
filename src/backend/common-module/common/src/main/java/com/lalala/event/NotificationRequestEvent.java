package com.lalala.event;

public record NotificationRequestEvent(Long uid, String email, String title, String content)
        implements LalalaEvent {
    @Override
    public String getTopic() {
        return TopicConstant.NOTIFICATION_REQUEST.value;
    }
}
