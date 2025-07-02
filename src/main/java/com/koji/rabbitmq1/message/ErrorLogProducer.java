package com.koji.rabbitmq1.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

/**
 * 전역 에러 핸들링 위한 프로듀서 설정
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ErrorLogProducer {
    private final StreamBridge streamBridge;

    public void sendErrorLog(Object errorPayload){
        log.info(">>>>sending : {}", errorPayload.toString());
        streamBridge.send("errorLogProducer-out-0",errorPayload);
    }
}
