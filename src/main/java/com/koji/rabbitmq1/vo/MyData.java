package com.koji.rabbitmq1.vo;

import lombok.Data;

/**
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
