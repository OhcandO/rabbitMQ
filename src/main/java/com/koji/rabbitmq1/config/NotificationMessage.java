package com.koji.rabbitmq1.config;

/**
 * packageName    : com.koji.rabbitmq1.config
 * fileName       : NotificationMessage
 * date           : 2025-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-01                           최초 생성
 */
public class NotificationMessage {
    private final String message;

    // 기본 생성자 (필수)
    public NotificationMessage() {
        message = "";
    }

    // 선택
    public NotificationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
