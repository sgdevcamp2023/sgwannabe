package com.lalala.alarm.service;

import com.lalala.alarm.dto.CreateNotificationRequestDTO;
import com.lalala.alarm.dto.NotificationDTO;
import com.lalala.alarm.dto.UpdateNotificationRequestDTO;
import com.lalala.alarm.entity.NotificationEntity;
import com.lalala.alarm.entity.User;
import com.lalala.alarm.repository.NotificationRepository;
import com.lalala.alarm.util.NotificationUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationRequestService {
    private final NotificationRepository repository;

    @Transactional
    public NotificationDTO createNotification(CreateNotificationRequestDTO request) {
        NotificationEntity notification = new NotificationEntity(
                request.getMessage(),
                new User(
                        request.getSender().getId(),
                        request.getSender().getNickname(),
                        request.getSender().getProfile()
                ),
                new User(
                        request.getReceiver().getId(),
                        request.getReceiver().getNickname(),
                        request.getReceiver().getProfile()
                ),
                request.getType()
        );
        notification = repository.save(notification);
        return NotificationDTO.from(notification);
    }

    @Transactional
    public List<NotificationDTO> getNotifications(Long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by(Direction.DESC, "id")
        );
        List<NotificationEntity> notifications = repository
                .findAllByReceiverIdOrderByIdDesc(id, pageable)
                .getContent();

        notifications.forEach(NotificationEntity::read);
        repository.saveAll(notifications);

        return notifications.stream()
                .map(NotificationDTO::from)
                .toList();
    }

    @Transactional
    public NotificationDTO updateNotification(Long id, UpdateNotificationRequestDTO request) {
        NotificationEntity notification = NotificationUtils.findById(id, repository);
        notification.update(
            notification.getMessage(),
            notification.getSender(),
            notification.getReceiver(),
            notification.getType()
        );
        return NotificationDTO.from(notification);
    }

    @Transactional
    public NotificationDTO deleteNotification(Long id) {
        NotificationEntity notification = NotificationUtils.findById(id, repository);
        repository.delete(notification);
        return NotificationDTO.from(notification);
    }
}
