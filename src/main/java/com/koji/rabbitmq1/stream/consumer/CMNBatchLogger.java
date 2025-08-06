package com.koji.rabbitmq1.stream.consumer;

import com.koji.rabbitmq1.stream.mapper.MessagePutMapper;
import com.koji.rabbitmq1.stream.vo.SiteTimeMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CMNBatchLogger /*extends CommonBatchExecutorService*/{

    /**
     * 메시지 로그 종합 처리 담당
     */
    @Bean
    public Consumer<List<SiteTimeMessageVO>> collectMessage() {
        return voList->{
            log.info(voList.toString());
//            executeBatch(MessagePutMapper.class, voList, 200, MessagePutMapper::mergeMessage);
        };
    }
}
