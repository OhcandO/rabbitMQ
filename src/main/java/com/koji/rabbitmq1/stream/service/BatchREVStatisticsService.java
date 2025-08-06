package com.koji.rabbitmq1.stream.service;

import com.koji.rabbitmq1.stream.constant.PropertiesBatchREVStatistics;
import com.koji.rabbitmq1.stream.publisher.CMNBatchProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * REV 서비스에서 시작하는 Batch 시작을 알리는 메시지 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchREVStatisticsService {

    private final CMNBatchProducer batchPublisher;
    private final PropertiesBatchREVStatistics revStatsProp;

    /**
     * 블록 야간최소유량 집계
     * siteHolder (siteSn, locgovCd) 만 필요.
     * 하루 한 번 아침에 수행
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.rev-statistics.items.mnf.cron}")
    public void updateMNF(){
        String THE_KEY = "mnf";
        batchPublisher.excuteSiteTimeBatch(revStatsProp.getDestination(), revStatsProp.getItems().get(THE_KEY),
                this.getClass().getName()+"::updateMNF");
    }


    /**
     * 공급량,사용량,무수량,유수율 집계
     * siteHolder (siteSn, locgovCd) 만 필요.
     * 하루 한 번 아침에 수행
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.rev-statistics.items.revenue.cron}")
    public void updateRevenue(){
        String THE_KEY = "revenue";
        batchPublisher.excuteSiteTimeBatch(revStatsProp.getDestination(), revStatsProp.getItems().get(THE_KEY),
                this.getClass().getName()+"::updateRevenue");
    }


    /** TODO
     * 시설물 이상치 카운트 갱신
     * siteHolder (siteSn, locgovCd) 만 필요.
     * 하루 한 번 아침에 수행
     */
    @Profile("!local")
//    @Scheduled(cron = "${batch.rev-statistics.items.mnf.cron}")
    public void updateAbnormality(){
        String THE_KEY = "abnormal";
        batchPublisher.excuteSiteTimeBatch(revStatsProp.getDestination(), revStatsProp.getItems().get(THE_KEY),
                this.getClass().getName()+"::updateAbnormality");
    }



}
