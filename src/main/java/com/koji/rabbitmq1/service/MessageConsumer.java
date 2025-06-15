package com.koji.rabbitmq1.service;

import com.koji.rabbitmq1.vo.ConfigUpdateMessage;
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

    public Consumer<ConfigUpdateMessage> configConsumer(){
        return message->{
            log.info("Set this config :{}",message.getConfigKey());
        };
    }
}
