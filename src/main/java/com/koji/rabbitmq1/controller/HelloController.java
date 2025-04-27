package com.koji.rabbitmq1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.koji.rabbitmq1.Controller
 * fileName       : HelloController
 * date           : 2025-04-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-23                           최초 생성
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloReturn (@RequestParam String name) {
        return "Hello " + name + "!";
    }
}
