package com.koji.rabbitmq1.stream.consumer;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.vo.CommonMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 */
@Slf4j
@RequiredArgsConstructor
abstract class CommonMessageConsumer<T extends CommonMessageVO> {

    private final StreamBridge streamBridge;
    @Value("${spring.cloud.stream.bindings.collectMessage-in-0.destination}")
    String MESSAGE_COLLECTOR_EXCHANGE_NAME;

    abstract String getReceiverName();

    protected Consumer<T> initConsume (Consumer<T> consumer) {
        return (CommonMSGVO) -> {
            CommonMSGVO.setReceiverName(getReceiverName());
            CommonMSGVO.setMessageReceiveTime(LocalDateTime.now());
            renewMessageState(CommonMSGVO, MessageState.RECEIVE);
            try {
                consumer.accept(CommonMSGVO);
            } catch (Exception e){
                log.error(e.getMessage());
                renewMessageState(CommonMSGVO, MessageState.ABORTED);
                //처리 실패한 경우 로직 필요
            }
            CommonMSGVO.setMessageCompleteTime(LocalDateTime.now());
            renewMessageState(CommonMSGVO, MessageState.COMPLETE);
        };
    }

    private void renewMessageState(T vo, int messageState){
        vo.setCurrentState(messageState);
        streamBridge.send(MESSAGE_COLLECTOR_EXCHANGE_NAME, vo);
    }

}