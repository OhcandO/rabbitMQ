package com.koji.rabbitmq1.stream.producer;

import com.koji.rabbitmq1.vo.BatchMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@Component
public class TestPublisher extends CommonMessageProducer<BatchMessageVO> {

    public TestPublisher(StreamBridge streamBridge) {
        super(streamBridge);
    }

    @Override
    String getPublisherName() {
        return this.getClass().getName();
    }

}
