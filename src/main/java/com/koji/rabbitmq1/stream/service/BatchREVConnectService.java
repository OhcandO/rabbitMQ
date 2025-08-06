package com.koji.rabbitmq1.stream.service;

import com.koji.rabbitmq1.stream.constant.PropertiesBatchREVConnect;
import com.koji.rabbitmq1.stream.constant.PropertyItem;
import com.koji.rabbitmq1.stream.publisher.CMNBatchProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * REV 서비스에서 시작하는 Batch 시작을 알리는 메시지 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BatchREVConnectService {

    private final CMNBatchProducer batchPublisher;
    private final PropertiesBatchREVConnect revProp;

    /**
     * 워터넷 태그 정보 수집.
     * siteHolder (siteSn, locgovCd) 만 필요.
     * 매 시간 수행
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.rev-connect.items.waternet-taginfo.cron}")
    public void mergeWaternetTagInfo(){
        String THE_KEY = "waternet-taginfo";
        batchPublisher.excuteSiteTimeBatch(revProp.getDestination(), revProp.getItems().get(THE_KEY),this.getClass().getName()+"::mergeWaternetTagInfo");
    }

    /**
     * 워터넷 태그 이력 수집.
     * siteTimeHolder (siteSn, locgovCd, searchStHm, searchEdHm) 필요.
     * 매 분 수행
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.rev-connect.items.waternet-tagcollect.cron}")
    public void mergeWaternetTagCollect(){
        String THE_KEY = "waternet-tagcollect";
        LocalDateTime startTime = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.now().withSecond(0).withNano(0);
        PropertyItem prop = revProp.getItems().get(THE_KEY);
        if (prop.getInterval() != null && !prop.getInterval().isEmpty()) {
            startTime = startTime.minusMinutes(Long.parseLong(prop.getInterval()));
        }
        batchPublisher.excuteSiteTimeBatch(revProp.getDestination(), revProp.getItems().get(THE_KEY),this.getClass().getName()+"::mergeWaternetTagCollect",startTime,endTime);
    }

    /**
     * 워터넷 수용가 사용량 수집.
     * siteTimeHolder (siteSn, locgovCd, searchStHm, searchEdHm), 월단위 시간.
     * 매 달 수행
     */
    @Profile("!local")
    @Scheduled(cron = "${batch.rev-connect.items.waternet-usage.cron}")
    public void mergeWaternetUsage(){
        String THE_KEY = "waternet-usage";
        batchPublisher.excuteSiteTimeBatch(revProp.getDestination(), revProp.getItems().get(THE_KEY),this.getClass().getName()+"::mergeWaternetUsage");
    }

}
