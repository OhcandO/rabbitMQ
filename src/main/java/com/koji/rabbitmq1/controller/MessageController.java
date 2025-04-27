package com.koji.rabbitmq1.controller;

import com.koji.rabbitmq1.config.WorkqueueProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.koji.rabbitmq1.controller
 * fileName       : MessageController
 * date           : 2025-04-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-27                           최초 생성
 */
@RestController
@RequestMapping("/msg")
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final WorkqueueProducer workqueueProducer;

    @PostMapping("/send")
    public String sendMassage(@RequestParam String msg, @RequestParam int duration){
        workqueueProducer.sendWorkqueue(msg, duration);
        return "[**] message sent successfully!" + msg+"/"+duration;
    }
}
