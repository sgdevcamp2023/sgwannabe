package com.lalala.alarm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class NotificationRequestEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "is_reserved", columnDefinition = "TINYINT")
    private Boolean isReserved = false;

    @Column(nullable = true, name = "reserved_at")
    private LocalDateTime reservedAt = null;

    public NotificationRequestEntity(
            Long uid,
            String title,
            String content,
            Boolean isReserved,
            LocalDateTime reservedAt
    ) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.isReserved = isReserved;
        this.reservedAt = reservedAt;
    }
}
