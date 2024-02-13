package com.lalala.alarm.dto;

import com.lalala.alarm.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateNotificationRequestDTO {
    private String message;

    private UserDTO sender;
    private UserDTO receiver;

    private boolean isViewed;
    private LocalDateTime viewedAt;

    private NotificationType type;
}
