package com.koji.rabbitmq1.vo;

import lombok.Data;

/**
 * packageName    : com.koji.rabbitmq1.vo
 * fileName       : ErrorLogMessage
 * date           : 2025-06-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-15                           최초 생성
 */
@Data
public class CommandMessage {
    private String commandType;
    private String payload;
}
