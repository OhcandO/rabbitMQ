package com.koji.rabbitmq1.stream.constant;

/**
 */
public interface MessageState {
    /**메시지 생성되어 발행되기 직전*/
    String ENQUEUED = "redy";
    /**메시지 발송 직후*/
    String SENT = "sent";
    /**메시지 수진 직후*/
    String RECEIVED = "rcvd";
    /**메시지 처리중 (대량데이터) */
    String PROCEED = "ing";
    /**메시지 처리 직후*/
    String COMPLETE = "done";
    /**메시지 처리중단됨 */
    String ABORTED = "abrt";
    /**메시지 거절(수신처없음) */
    String REJECTED = "rjct";
}
