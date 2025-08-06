package com.koji.rabbitmq1.stream.service;

import com.koji.rabbitmq1.stream.constant.PropertiesBatchIOT;
import com.koji.rabbitmq1.stream.publisher.CMNBatchProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * IOT 서비스에서 시작해야하는 BATCH 를 시작하는 메시지 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchIOTService {

    private final CMNBatchProducer batchPublisher;
    private final PropertiesBatchIOT sfProp;

    /**
     * 유솔 소규모유량계 API 로 데이터 수집.
     * 일 단위 파라미터 적용.
     * 아마 매 시간 수행되어야 할 것
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.iot.items.smallflow.cron}")
    public void initBatchIOTSmallFlowSource() {
        String THE_KEY = "smallflow";

        batchPublisher.excuteSiteTimeBatch(sfProp.getDestination(), sfProp.getItems().get(THE_KEY),this.getClass().getName()+"::initBatchIOTSmallFlowSource");
    }

    /**
     * 유솔 소규모유량계 API 로 원격검침(스마트미터) 수용가 사용량 데이터 수집.
     * 일 단위 파라미터 적용.
     * 아마 매 시간 수행
     * !!현재 수용가 사용량은 워터넷으로만 집계하기 때문에 수집하지 않음
     */
    @Profile("!local")
//    @Scheduled(cron = "${batch.iot.items.usage.cron}")
    public void initBatchIOTUsageSource(){
        String THE_KEY = "usage";
        batchPublisher.excuteSiteTimeBatch(sfProp.getDestination(), sfProp.getItems().get(THE_KEY), this.getClass().getName()+"::initBatchIOTUsageSource");
    }
    /**
     * 유솔 소규모유량계 API 로 누수센서 데이터 수집.
     * 일 단위 파라미터 적용.
     * 매 월요일마다
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.iot.items.leakage.cron}")
    public void initBatchIOTLeakageSource(){
        String THE_KEY = "leakage";
        batchPublisher.excuteSiteTimeBatch(sfProp.getDestination(), sfProp.getItems().get(THE_KEY), this.getClass().getName()+"::initBatchIOTLeakageSource");
    }

}
