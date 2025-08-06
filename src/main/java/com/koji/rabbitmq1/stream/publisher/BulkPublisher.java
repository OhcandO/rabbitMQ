package com.koji.rabbitmq1.stream.publisher;

import com.koji.rabbitmq1.stream.constant.MessageState;
import com.koji.rabbitmq1.stream.constant.MessageStateManager;
import com.koji.rabbitmq1.stream.constant.PropertiesBulkTagCollect;
import com.koji.rabbitmq1.stream.constant.PropertiesBulkTagInfo;
import com.koji.rabbitmq1.stream.vo.CommonMessageVO;
import com.koji.rabbitmq1.vo.MoMap;
import com.koji.rabbitmq1.vo.dto.holder.SiteHolder;
import com.koji.rabbitmq1.vo.dto.holder.SiteTimeSearchable;
import com.koji.rabbitmq1.vo.facility.SiteTimeDTO;
import com.koji.rabbitmq1.vo.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 대량 데이터를 설정한 버퍼크기, 배치크기 단위로 메시지화 시켜 보내는 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BulkPublisher {

    private final StreamBridge streamBridge;
    private final PropertiesBulkTagInfo pTagInfo;
    private final PropertiesBulkTagCollect pTagCollect;
    private final MessageStateManager mm;

    private final DateTimeFormatter yyyymmddhh24mi = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * REV 로 IOT 에서 수집한 태그 정보 병합함
     */
    public void transferTagInfo(SiteHolder param){
//        List<MoMap> iotTagInfo = iotGetService.getIOTTagInfo(param);
        List<MoMap> iotTagInfo = new ArrayList<>();
        int batchSize = (pTagInfo.getBatchSize()!=null && !pTagInfo.getBatchSize().isEmpty()) ?
                Integer.parseInt(pTagInfo.getBatchSize())
                : 50;
        String destination = pTagInfo.getDestination();
        String routingKey = pTagInfo.getBindingKey();

        CommonMessageVO vo = new CommonMessageVO();
        vo.setMessageFireTime(LocalDateTime.now());
        vo.setPublisherName(this.getClass().getName()+"::transferTagInfo");
        vo.setNote("transferTagInfo | " + pTagInfo.getNote());
        mm.renewMessageState(vo, MessageState.ENQUEUED);

        batchSending(batchSize, destination, routingKey, iotTagInfo, vo);

        vo.setNote(vo.getNote() + "[총 "+iotTagInfo.size()+"건]");
        mm.renewMessageState(vo, MessageState.SENT);
    }

    /**
    * REV 로 IOT 에서 수집한 순시 태그 이력 데이터 전달
    */
    public void transferTagCollect(SiteTimeSearchable param){
        param.timeMode(ChronoUnit.MINUTES);
        int bufferSize = (pTagCollect.getBufferSize()!=null && !pTagCollect.getBufferSize().isEmpty())
                        ? Integer.parseInt(pTagCollect.getBufferSize())
                        : 5000;
        int batchSize = (pTagCollect.getBatchSize()!=null && !pTagCollect.getBatchSize().isEmpty()) ?
                        Integer.parseInt(pTagCollect.getBatchSize())
                        : 50;
        String destination = pTagCollect.getDestination();
        String routingKey = pTagCollect.getBindingKey();
        
        CommonMessageVO vo = new CommonMessageVO();
        vo.setMessageFireTime(LocalDateTime.now());
        vo.setPublisherName(this.getClass().getName()+"::transferTagCollect");
        vo.setNote("transferTagCollect | " + pTagCollect.getNote());
        mm.renewMessageState(vo, MessageState.ENQUEUED);

        List<MoMap> collectList = new ArrayList<>();
        int totalCount = 0;

        List<String[]> timingList = TimeUtil.getStartFinalTimeStr(param, ChronoUnit.MINUTES, bufferSize);

        for (String[] timingStr : timingList){
            SiteTimeSearchable localParam = new SiteTimeDTO(param);
            param.setSearchStHm(timingStr[0]);
            param.setSearchEdHm(timingStr[1]);
//            log.info(">>> 조회파람 : {}",param);

            if (collectList.size() >= bufferSize) {
                totalCount += collectList.size();
                batchSending(batchSize, destination, routingKey, collectList, vo);
                collectList.clear();
            }
        }
        if (!collectList.isEmpty()){
            totalCount += collectList.size();
            batchSending(batchSize, destination, routingKey, collectList, vo);
        }
        vo.setNote(vo.getNote() + "[총 "+totalCount+"건]");
        mm.renewMessageState(vo, MessageState.SENT);
    }


    /**
     * 메시지 일반 상태를 header 에 기입
     */
    private void batchSending(int batchSize, String bindingName, String routingKey, List<MoMap> sourceList, CommonMessageVO vo){
        int total = sourceList.size();
        int batchCount = (int) Math.ceil((double) total / batchSize);

        for (int i =0 ; i<batchCount ; i++){
            int st = i * batchSize;
            int ed = Math.min(st + batchSize, total);

            List<MoMap> eachList = new ArrayList<>( sourceList.subList(st, ed));

            Message<List<MoMap>> eachMessage = MessageBuilder.withPayload(eachList)
                    .setHeader("routingKey", routingKey)
                    .setHeader("msgId", vo.getId())
                    .setHeader("isFirst", i == 0)
                    .setHeader("isLast", i == batchCount - 1)
                    .build();

            boolean isSent = streamBridge.send(bindingName, eachMessage);
            if (isSent) mm.renewMessageState(vo, MessageState.PROCEED);
            else mm.renewMessageState(vo, MessageState.ABORTED);
            log.info("[sending from IOT] {} - size :{}", i, eachList.size());
        }
    }

}
