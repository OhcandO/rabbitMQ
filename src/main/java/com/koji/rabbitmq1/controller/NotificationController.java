package com.koji.rabbitmq1.controller;

import com.koji.rabbitmq1.config.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.koji.rabbitmq1.controller
 * fileName       : NotificationPublisher
 * date           : 2025-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-01                           최초 생성
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationPublisher notificationPublisher;

    @PostMapping
    public String sendNotification(@RequestBody String message){
        notificationPublisher.publish(message);
        return "[#] Notification sent: "+message;
    }
}
