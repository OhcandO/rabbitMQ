package com.koji.rabbitmq1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
public class RabbitMq1Application {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMq1Application.class, args);
    }

}
