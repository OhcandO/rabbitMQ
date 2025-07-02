package com.koji.rabbitmq1.service;

import com.koji.rabbitmq1.vo.ConfigUpdateMessage;
import com.koji.rabbitmq1.vo.ErrorLogMessage;
import com.koji.rabbitmq1.vo.MyData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * packageName    : com.koji.rabbitmq1.service
 * fileName       : MessageConsumer
 * date           : 2025-06-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-15                           최초 생성
 */
@Component
@Slf4j
public class MessageConsumer {

    @Bean
    public Consumer<MyData> bulkDataConsumer(){
        return message->{
            log.info("MyData received :{}",message.getId());
        };
    }

    @Bean
    public Consumer<ErrorLogMessage> errorLogConsumer(){
        return message->{
            String errorType = message.getErrorType();
            String errorMessage = message.getErrorMessage();
            long timestamp = message.getTimestamp();
            log.warn("<<<incoming<<:{}/{}/{}",errorType,errorMessage,timestamp);
        };
    }

    public Consumer<ConfigUpdateMessage> configConsumer(){
        return message->{
            log.info("Set this config :{}",message.getConfigKey());
        };
    }
}
