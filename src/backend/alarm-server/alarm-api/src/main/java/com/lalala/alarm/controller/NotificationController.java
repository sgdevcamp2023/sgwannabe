package com.lalala.alarm.controller;

import com.lalala.alarm.dto.CreateNotificationRequestDTO;
import com.lalala.alarm.dto.NotificationDTO;
import com.lalala.alarm.dto.UpdateNotificationRequestDTO;
import com.lalala.alarm.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/notifications")
public class NotificationController {
    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody CreateNotificationRequestDTO request) {
        NotificationDTO notification = service.createNotification(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificationDTO>> readNotifications(
            @PathVariable("id") Long id,
            @RequestParam(required = false, defaultValue = "0", name = "page") int page,
            @RequestParam(required = false, defaultValue = "50", name = "size") int pageSize
    ) {
        List<NotificationDTO> notifications = service.getNotifications(id, page, pageSize);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @PathVariable("id") Long id,
            @RequestBody UpdateNotificationRequestDTO request
    ) {
        NotificationDTO notification = service.updateNotification(id, request);
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        NotificationDTO notification = service.deleteNotification(id);
        return ResponseEntity.ok(notification);
    }
}
