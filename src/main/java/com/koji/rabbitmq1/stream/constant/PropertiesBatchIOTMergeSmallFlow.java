package com.koji.rabbitmq1.stream.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 */
@Data
@ConfigurationProperties(prefix = "batch.iot.merge-smallflow")
public class PropertiesBatchIOTMergeSmallFlow {
    private String note;
    private String destination;
    private String cron;
    private String bindingKey;
    private String interval;
}
