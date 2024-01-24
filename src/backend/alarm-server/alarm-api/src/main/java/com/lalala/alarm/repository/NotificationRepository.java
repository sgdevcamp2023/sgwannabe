package com.lalala.alarm.repository;

import com.lalala.alarm.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Page<NotificationEntity> findAllByReceiverIdOrderByIdDesc(Long receiverId, Pageable pageable);
}
