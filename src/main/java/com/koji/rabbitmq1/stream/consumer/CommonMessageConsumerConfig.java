package com.koji.rabbitmq1.stream.consumer;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.stream.vo.CommonMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 */
@Slf4j
@RequiredArgsConstructor
abstract class CommonMessageConsumerConfig<T extends CommonMessageVO> {

    private final MessageStateManager mm;

    abstract String getReceiverName();

    protected Consumer<T> initConsume (Consumer<T> consumer) {
        return (CommonMSGVO) -> {
            CommonMSGVO.setReceiverName(getReceiverName());
            CommonMSGVO.setMessageReceiveTime(LocalDateTime.now().withSecond(0).withNano(0));
            mm.renewMessageState(CommonMSGVO, MessageState.RECEIVED);
            try {
                consumer.accept(CommonMSGVO);
            } catch (Exception e){
                log.error(e.getMessage());
                mm.renewMessageState(CommonMSGVO, MessageState.ABORTED);
                //처리 실패한 경우 로직 필요
            }
            CommonMSGVO.setMessageCompleteTime(LocalDateTime.now());
            mm.renewMessageState(CommonMSGVO, MessageState.COMPLETE);
        };
    }

}