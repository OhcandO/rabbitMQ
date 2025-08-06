package com.koji.rabbitmq1.stream.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 *
 */
@Data
@ConfigurationProperties(prefix = "batch.rev-statistics")
public class PropertiesBatchREVStatistics {
    private String destination;
    private Map<String, PropertyItem> items;
}
