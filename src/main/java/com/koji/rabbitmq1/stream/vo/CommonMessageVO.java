package com.koji.rabbitmq1.stream.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/** 일반 메세지 VO */
@Data
public class CommonMessageVO {

    /** 유니크 아이디     */
    String id = UUID.randomUUID().toString();
    /** 메시지 발송시간     */
    LocalDateTime messageFireTime;
    /** 메시지 수신시간     */
    LocalDateTime messageReceiveTime;
    /** 메시지 처리시간     */
    LocalDateTime messageCompleteTime;

    /** 발송 서비스 명     */
    String publisherName;

    /** 수신 서비스 명     */
    String receiverName;

    /** 메시지 목적 */
    String note;
    /** 메시지 상태     */
    String currentState;
}