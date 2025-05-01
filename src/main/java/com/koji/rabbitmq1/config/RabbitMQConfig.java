package com.koji.rabbitmq1.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : RabbitMQConfig
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@Configuration
public class RabbitMQConfig {


    // 큐 네임을 설정한다.
    public static final String QUEUE_NAME = "notificationQueue";
    public static final String FANOUT_EXCHANGE = "notificationExchange";

    /**
     *  역할: 이 Bean은 Queue 인스턴스를 생성하고, 애플리케이션이 사용할 RabbitMQ 큐를 정의합니다.
      *  QUEUE_NAME은 메시지가 쌓이고 처리될 큐의 이름을 정의합니다. 이 예제에서는 helloQueue로 설정되어 있습니다.
      * 	false 파라미터는 큐가 휘발성(volatile)인지 영속성(persistent)인지 여부를 지정하는 옵션입니다. false로 설정하면 서버가 종료되거나 재시작될 때 큐의 메시지가 사라집니다.
      * 	사용 용도: 메시지를 전달하고 처리하는 기본 큐를 설정하는 데 사용됩니다.
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindNotification(Queue notificationQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notificationQueue).to(fanoutExchange);
    }
}
