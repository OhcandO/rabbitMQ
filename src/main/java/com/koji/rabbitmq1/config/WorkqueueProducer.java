package com.koji.rabbitmq1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : WorkqueueProducer
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WorkqueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendWorkqueue (String msg, int duration){
        String sentMsg = msg+"|"+duration;
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, sentMsg);
        log.info("[>>] Sent workqueue : {}",sentMsg);
    }
}
