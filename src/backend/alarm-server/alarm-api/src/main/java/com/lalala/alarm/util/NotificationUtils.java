package com.lalala.alarm.util;

import com.lalala.alarm.entity.NotificationEntity;
import com.lalala.alarm.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationUtils {
    public static NotificationEntity findById(Long id, NotificationRepository repository) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("아티스트를 조회할 수 없습니다."));
    }
}
