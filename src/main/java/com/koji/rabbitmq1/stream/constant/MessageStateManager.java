package com.koji.rabbitmq1.stream.constant;

import com.koji.rabbitmq1.stream.vo.CommonMessageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * packageName    : kr.co.mo.revservice.stream.constant
 * fileName       : MessageStateManager
 * date           : 2025-08-05
 */
@Component
@RequiredArgsConstructor
public class MessageStateManager {

    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.defaultCollectionDestination}")
    String theBindingName;
    /**
     * 메시지 상태 변화를 기록
     */
    public void renewMessageState(CommonMessageVO vo, String messageState) {
        vo.setCurrentState(messageState);

        if (MessageState.SENT.equals(messageState)) {
            vo.setMessageFireTime(LocalDateTime.now());
        }
        if (MessageState.RECEIVED.equals(messageState)) {
            vo.setMessageReceiveTime(LocalDateTime.now());
        }
        if (MessageState.COMPLETE.equals(messageState)) {
            vo.setMessageCompleteTime(LocalDateTime.now());
        }
        streamBridge.send(theBindingName, MessageBuilder.withPayload(vo).setHeader("routingKey", theBindingName).build());
    }

}
