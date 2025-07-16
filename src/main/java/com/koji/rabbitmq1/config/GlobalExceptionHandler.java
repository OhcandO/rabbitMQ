package com.koji.rabbitmq1.config;

import com.koji.rabbitmq1.stream.producer.ErrorLogProducer;
import com.koji.rabbitmq1.vo.ErrorLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 에러 핸들러
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final ErrorLogProducer errorLogProducer;
    public GlobalExceptionHandler(ErrorLogProducer errorLogProducer) {
        this.errorLogProducer = errorLogProducer;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex){
        ErrorLogMessage errorLogMessage = ErrorLogMessage.builder()
                .errorType(ex.getClass().getSimpleName())
                .errorMessage(ex.getMessage())
                .serviceName(this.getClass().getSimpleName())
                .timestamp(System.currentTimeMillis())
                .build();
        log.warn("@@@at exception handler : {}",errorLogMessage.toString());
        Map<String,String> tempMap = new HashMap<>();
        tempMap.put("errorType",ex.getClass().getSimpleName());
        tempMap.put("errorMessage",ex.getMessage());
        errorLogProducer.sendErrorLog(tempMap);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorLogMessage.toString());
    }
}
