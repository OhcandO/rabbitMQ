package com.koji.rabbitmq1.vo;

import lombok.Data;

/**
 * 가상의 대량 데이터 VO
 */
@Data
public class MyData {
    private String id;
    private String dateStr;
    private String value;

    public MyData(String id, String dateStr, String value) {
        this.id = id;
        this.dateStr = dateStr;
        this.value = value;
    }
}
