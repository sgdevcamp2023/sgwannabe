package com.lalala.alarm.dto;

import com.lalala.alarm.entity.NotificationEntity;
import com.lalala.alarm.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationDTO {
    private final Long id;
    private final String message;

    private final UserDTO sender;
    private final UserDTO receiver;

    private final boolean isViewed;
    private final LocalDateTime viewedAt;

    private final NotificationType type;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static NotificationDTO from(
            NotificationEntity notification
    ) {
        return new NotificationDTO(
            notification.getId(),
            notification.getMessage(),
            UserDTO.from(notification.getSender()),
            UserDTO.from(notification.getReceiver()),
            notification.getIsViewed(),
            notification.getViewedAt(),
            notification.getType(),
            notification.getCreatedAt(),
            notification.getUpdatedAt()
        );
    }
}
