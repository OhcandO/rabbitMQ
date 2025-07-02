package com.koji.rabbitmq1.controller;

import com.koji.rabbitmq1.service.DataProducer;
import com.koji.rabbitmq1.vo.MyData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 */
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final DataProducer dataProducer;

    @GetMapping("/error")
    public ResponseEntity<String> errorAPI() {
        String value = null;
        value.getBytes(); // null pointer
        return ResponseEntity.ok("Controller Nullpointer Exception 처리 ");
    }

    @GetMapping("/info")
    public ResponseEntity<String> infoAPI(@RequestParam("msg") String message) {

        List<MyData> theList = new ArrayList<>();
        LocalDateTime theTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i=0; i<400; i++){
            theList.add(new MyData("id_"+i, theTime.format(formatter), message+2*i ));
        }

        dataProducer.sendData(theList);

        return ResponseEntity.ok("Controller Info log 발송 처리 ");
    }

}