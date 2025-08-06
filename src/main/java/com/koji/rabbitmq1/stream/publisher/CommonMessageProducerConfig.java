package com.koji.rabbitmq1.stream.publisher;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.stream.vo.CommonMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

/**
 * 서비스에서 발행되는 메시지를 발송하는 것과 더불어, 메시지 자체를 기록하는 로직도 포함<br/>
 * 메시지 기록하는 것은 매번 쿼리 발송이 아니라 이 또한 메시지 처리로 돌림
 */
@RequiredArgsConstructor
@Slf4j
abstract class CommonMessageProducerConfig<T extends CommonMessageVO> {

    private final StreamBridge streamBridge;
    private final MessageStateManager mm;

    /**
     * 메시지 발행 Wrapper. exchange type:direct. 특정 queue에 전달한다는 의도를 분명히 함
     * @param vo 일반 메시지 객체
     * @param bindingName 목적지 (exchange / destination)
     * @param routingKey 바인딩 키
     * @param publisherName 발행하는 메서드 이름
     * @param note 메시지 노트
     */
    public void publishDirect(String bindingName, T vo, String routingKey,String publisherName, String note){
        vo.setPublisherName(publisherName);
        vo.setMessageFireTime(LocalDateTime.now().withSecond(0).withNano(0));
        vo.setNote(note);
        mm.renewMessageState(vo, MessageState.ENQUEUED);
//        log.info("      [msg] exchange: {} || routingKey: {}",exchange, routingKey);
        boolean isSent = streamBridge.send(bindingName, MessageBuilder.withPayload(vo).setHeader("routingKey", routingKey).build());
        if (isSent){
            mm.renewMessageState(vo, MessageState.SENT);
        }
    }

}
