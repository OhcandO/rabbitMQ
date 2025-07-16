package com.koji.rabbitmq1.stream.producer;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.vo.CommonMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

/**
 * 서비스에서 발행되는 메시지를 발송하는 것과 더불어, 메시지 자체를 기록하는 로직도 포함<br/>
 * 메시지 기록하는 것은 매번 쿼리 발송이 아니라 이 또한 메시지 처리로 돌림
 */
@RequiredArgsConstructor
@Slf4j
abstract class CommonMessageProducer<T extends CommonMessageVO> {

    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.bindings.collectMessage-in-0.destination}")
    String MESSAGE_COLLECTOR_EXCHANGE_NAME;

    abstract String getPublisherName();

    /**
     * 메시지 발행 Wrapper. exchange type:direct. 특정 queue에 전달한다는 의도를 분명히 함
     * @param vo 일반 메시지 객체
     * @param exchange 목적지 (exchange / destination)
     * @param routingKey 바인딩 키
     * @param note 메시지 노트
     */
    public void publishDirect(T vo, String exchange, String routingKey, String note){
        vo.setPublisherName(getPublisherName());
        vo.setMessageFireTime(LocalDateTime.now().withSecond(0).withNano(0));
        vo.setNote(note);
        renewMessageState(vo, MessageState.ENQUEUED);
        boolean isSent = streamBridge.send(exchange, MessageBuilder.withPayload(vo).setHeader("routingKey", routingKey).build());
        if (isSent){
            renewMessageState(vo, MessageState.SEND);
        }
    }

    /**
     * exchange type:topic. general 목적
     * @param vo 일반 메시지 객체
     * @param exchange 목적지 (exchange / destination)
     * @param note 메시지 노트
     */
    public void publish(T vo, String exchange, String note){
        vo.setPublisherName(getPublisherName());
        vo.setMessageFireTime(LocalDateTime.now().withSecond(0).withNano(0));
        vo.setNote(note);
        renewMessageState(vo, MessageState.ENQUEUED);
        boolean isSent = streamBridge.send(exchange, vo);
        if (isSent){
            renewMessageState(vo, MessageState.SEND);
        }
    }

    private void renewMessageState(T vo, int messageState){
        vo.setCurrentState(messageState);
        streamBridge.send(MESSAGE_COLLECTOR_EXCHANGE_NAME, vo);
    }
}
