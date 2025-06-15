package com.koji.rabbitmq1.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : CustomExceptionHandler
 * date           : 2025-05-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-09                           최초 생성
 */
@Component
@Slf4j
public class CustomExceptionHandler {
//    private final LogPublisher logPublisher;
//
//    public CustomExceptionHandler(LogPublisher logPublisher) {
//        this.logPublisher = logPublisher;
//    }

    // 에러나 로그 처리
    public void handleException(Exception e) {
        String message = e.getMessage();

        String routingKey;

        if (e instanceof NullPointerException) {
            routingKey = "error";
        } else if (e instanceof IllegalArgumentException) {
            routingKey = "warn";
        } else {
            routingKey = "error";
        }
        log.warn("Exception이 발생했음 : {}|{}", routingKey, message);
//        logPublisher.publish(routingKey, "Exception이 발생했음 : " + message);
    }

    // 메시지 처리
    public void handleMessage(String message) {
        String routingKey = "info";
        log.info("{}|{}", routingKey, message);
//        logPublisher.publish(routingKey, "Info Log : " + message);
    }
}
