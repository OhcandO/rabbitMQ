package com.koji.rabbitmq1.config;

import com.koji.rabbitmq1.vo.ErrorLogMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

/**
 */
@Component
public class MessageProducer {

    private final Queue<ErrorLogMessage> errorLogQueue = new LinkedList<>();

    @Bean
    public Supplier<ErrorLogMessage> send(){
        return () -> errorLogQueue.poll();
    }


}
