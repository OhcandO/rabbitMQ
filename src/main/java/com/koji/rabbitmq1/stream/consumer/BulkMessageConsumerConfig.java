package com.koji.rabbitmq1.stream.consumer;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.stream.vo.CommonMessageVO;
import com.koji.rabbitmq1.vo.MoMap;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * Batch 로 대량 데이터 처리하면서, 메시지 상태 처리를 동시에 처리하는 컬래스
 */
abstract class BulkMessageConsumerConfig {

    private final MessageStateManager mm;

    public BulkMessageConsumerConfig(MessageStateManager mm) {
        this.mm = mm;
    }

    abstract String getServiceName();

    protected Consumer<Message<List<MoMap>>> initConsume(Consumer<List<MoMap>> consumer){
           return (mssg -> {
               MessageHeaders headers = mssg.getHeaders();
               String msgId = (String) headers.get("msgId");

               CommonMessageVO vo = new CommonMessageVO();
               vo.setId(msgId);
               vo.setReceiverName(getServiceName());
               boolean isFirst = Boolean.TRUE.equals(headers.get("isFirst"));
               if (isFirst) mm.renewMessageState(vo, MessageState.RECEIVED);
               else mm.renewMessageState(vo, MessageState.PROCEED);
               try{
                   consumer.accept(mssg.getPayload());
               }catch (RuntimeException e){
                   vo.setNote("[실패]" + e.getMessage());
                   mm.renewMessageState(vo, MessageState.ABORTED);
               }catch (Exception e){
                   mm.renewMessageState(vo, MessageState.ABORTED);
               }
               boolean isLast = Boolean.TRUE.equals(headers.get("isLast"));
               if (isLast) mm.renewMessageState(vo, MessageState.COMPLETE);
           });
       }
}
