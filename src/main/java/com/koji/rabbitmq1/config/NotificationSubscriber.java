package com.koji.rabbitmq1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : NotificationSubscriber
 * date           : 2025-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-01                           최초 생성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationSubscriber {

    public static final String CLIENT_URL = "/topic/notifications";
    private final SimpMessagingTemplate simpleMessagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void subscriber(String message){

        log.info("[<<-] Notification received: {}",message);

        simpleMessagingTemplate.convertAndSend(CLIENT_URL, message);
    }
}
