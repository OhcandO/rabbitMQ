package com.koji.rabbitmq1.stream.consumer;

import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.vo.MoMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * MQ 로 전달되는 Batch 를 확인해 처리
 */
@Service
@Slf4j
public class REVBulkConsumer extends BulkMessageConsumerConfig {

    public REVBulkConsumer(MessageStateManager mm) {
        super(mm);
    }

    @Override
    String getServiceName() {
        return this.getClass().getName();
    }

    /**
     * 대량 데이터로 들어온 태그 정보를 병합
     * 출처는 IOT 등
     */
    @Bean
    public Consumer<Message<List<MoMap>>> bulkTagInfo() {
        return initConsume(sourceList -> {
            if (sourceList==null || sourceList.isEmpty()) return;
            log.info("[receive at REV] 태그 정보 size - {}", sourceList.size());
        });
    }

    /**
     * 대량 데이터로 들어온 태그 정보를 병합
     * 출처는 IOT 등
     */
    @Bean
    public Consumer<Message<List<MoMap>>> bulkTagCollect() {
        return initConsume(sourceList -> {
            if (sourceList==null || sourceList.isEmpty()) return;
            String siteSn = sourceList.get(0).getString("siteSn");
            log.info("[receive at REV] 태그 이력 size - {}", sourceList.size());
        });
    }

}
