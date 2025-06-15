package com.koji.rabbitmq1.controller;

import com.koji.rabbitmq1.service.DataProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.koji.rabbitmq1.controller
 * fileName       : MessageController
 * date           : 2025-06-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-15                           최초 생성
 */
@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class MessageController {
    private final DataProducer dataProducer;


}
