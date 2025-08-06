package com.koji.rabbitmq1.stream.constant;

import lombok.Data;

/**
 */
@Data
public class PropertyItem {
    private String bindingKey;
    private String cron;
    private String note;
    private String interval;
}
