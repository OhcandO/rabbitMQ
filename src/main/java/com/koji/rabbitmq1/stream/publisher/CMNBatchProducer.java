package com.koji.rabbitmq1.stream.publisher;

import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.stream.constant.PropertyItem;
import com.koji.rabbitmq1.stream.vo.SiteTimeMessageVO;
import com.koji.rabbitmq1.vo.dto.holder.SiteHolder;
import com.koji.rabbitmq1.vo.facility.SiteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

/**
 * 모든 Batch 의 시작을 알리는 메시지 처리를 관장 
 */
@Slf4j
@Component
public class CMNBatchProducer extends CommonMessageProducerConfig<SiteTimeMessageVO> {

    private final DateTimeFormatter yyyymmddhh24mi = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public CMNBatchProducer(StreamBridge streamBridge, MessageStateManager mm) {
        super(streamBridge, mm);
    }

    public void excuteSiteTimeBatch(String bindingName, PropertyItem prop, String producerName){
        excuteSiteTimeBatch( bindingName, prop, producerName, null,null);
    }

    public void excuteSiteTimeBatch(String bindingName, PropertyItem prop, String producerName, LocalDateTime stdt, LocalDateTime eddt) {
        List<SiteHolder> targetSiteList = Collections.singletonList(new SiteDTO("1","45180"));

        targetSiteList.forEach(siteHolder -> {
            SiteTimeMessageVO batchVO = new SiteTimeMessageVO(siteHolder);

            LocalDateTime searchStHm ;
            if (stdt != null) {
                searchStHm = stdt;
            }else{
                searchStHm =  LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
            }

            batchVO.setSearchStHm(searchStHm.format(yyyymmddhh24mi));
            if(eddt!=null) batchVO.setSearchEdHm(eddt.format(yyyymmddhh24mi));
            batchVO.timeMode(ChronoUnit.MINUTES);
            publishDirect(bindingName, batchVO, prop.getBindingKey(), producerName, prop.getNote());
        });
    }

}
