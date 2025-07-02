package com.koji.rabbitmq1.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 에러메세지 VO
 */
@Data
@Builder
public class ErrorLogMessage {
    private String serviceName;
    private String errorType;
    private String errorMessage;
    private long timestamp;
}
