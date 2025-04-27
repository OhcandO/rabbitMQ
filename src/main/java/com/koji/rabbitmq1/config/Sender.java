package com.koji.rabbitmq1.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : Sender
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public void send(String msg){
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, msg);
        log.info("[##] sender sent msg : {}", msg);
    }
}
