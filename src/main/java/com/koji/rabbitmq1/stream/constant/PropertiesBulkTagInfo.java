package com.koji.rabbitmq1.stream.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 */
@Data
@ConfigurationProperties(prefix = "bulk.tag-info")
public class PropertiesBulkTagInfo {
    /** 메시지 한 건으로 보낼 크기 */
    private String batchSize;
    /** 긴 기간 조회 시 페이징 처리할 크기 */
    private String bufferSize;
    /** exchange name */
    private String destination;
    /** binding-routing key */
    private String bindingKey;
    /** 메시지 정의 */
    private String note;
}
