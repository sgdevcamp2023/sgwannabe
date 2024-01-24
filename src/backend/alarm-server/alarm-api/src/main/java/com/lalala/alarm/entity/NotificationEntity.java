package com.lalala.alarm.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "sender_id")),
            @AttributeOverride(name = "nickname", column = @Column(name = "sender_nickname")),
            @AttributeOverride(name = "profile", column = @Column(name = "sender_profile"))
    })
    private User sender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "receiver_id")),
            @AttributeOverride(name = "nickname", column = @Column(name = "receiver_nickname")),
            @AttributeOverride(name = "profile", column = @Column(name = "receiver_profile"))
    })
    private User receiver;

    @Column(name = "is_viewed", nullable = false, columnDefinition = "TINYINT")
    private Boolean isViewed = false;

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private NotificationType type = NotificationType.NORMAL;

    public NotificationEntity(
            String message,
            User sender,
            User receiver,
            NotificationType type
    ) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public void update(
            String message,
            User sender,
            User receiver,
            NotificationType type
    ) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public void read() {
        this.isViewed = true;
        this.viewedAt = LocalDateTime.now();
    }
}
