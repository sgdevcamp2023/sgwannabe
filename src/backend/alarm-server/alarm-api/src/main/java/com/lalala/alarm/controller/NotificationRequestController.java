package com.lalala.alarm.controller;

import com.lalala.alarm.service.NotificationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/notifications")
public class NotificationRequestController {
    private final NotificationRequestService service;
}
