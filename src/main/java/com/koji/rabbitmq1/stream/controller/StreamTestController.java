package com.koji.rabbitmq1.stream.controller;

import com.koji.rabbitmq1.stream.service.BatchIOTService;
import com.koji.rabbitmq1.stream.service.BatchREVConnectService;
import com.koji.rabbitmq1.stream.service.BatchREVStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : kr.co.mo.cmservice.stream.controller
 * fileName       : StreamTestController
 * date           : 2025-08-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-08-04                           최초 생성
 */
@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamTestController {

    private final BatchIOTService batchIOTService;
    private final BatchREVConnectService batchREVConnectService;
    private final BatchREVStatisticsService batchREVStatisticsService;

    @GetMapping("/test/iot")
    public ResponseEntity<String> testIOT(){
        batchIOTService.initBatchIOTSmallFlowSource();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }
    @GetMapping("/test/rev/waternet/tagInfo")
    public ResponseEntity<String> testWaternetTagInfo(){
        batchREVConnectService.mergeWaternetTagInfo();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }
    @GetMapping("/test/rev/waternet/tagCollect")
    public ResponseEntity<String> testWaternetTagCollect(){
        batchREVConnectService.mergeWaternetTagCollect();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }
    @GetMapping("/test/rev/waternet/usage")
    public ResponseEntity<String> testWaternetusage(){
        batchREVConnectService.mergeWaternetUsage();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }

    @GetMapping("/test/rev/stats/mnf")
    public ResponseEntity<String> test5(){
        batchREVStatisticsService.updateMNF();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }
    @GetMapping("/test/rev/stats/revenue")
    public ResponseEntity<String> test6(){
        batchREVStatisticsService.updateRevenue();
        return new ResponseEntity<>("testDone", HttpStatus.OK);
    }

}
