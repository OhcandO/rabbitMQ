package com.koji.rabbitmq1.config;

import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : Receiver
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@Component
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println("[#] Received: " + message);
    }
}
