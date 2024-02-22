package com.lalala.alarm.repository;

import com.lalala.alarm.entity.NotificationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRequestRepository extends JpaRepository<NotificationRequestEntity, Long> {
}
