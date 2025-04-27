package com.koji.rabbitmq1.controller;

import com.koji.rabbitmq1.config.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final Sender sender;

    @PostMapping("/send")
    public String sendMassage(@RequestBody String msg){
        sender.send(msg);
        return "[**] message sent successfully!" + msg;
    }
}
