package com.koji.rabbitmq1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : com.koji.rabbitmq1.controller
 * fileName       : Home
 * date           : 2025-05-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-01                           최초 생성
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home (Model model){
        model.addAttribute("message", "Hello World");
        return "home";
    }
}
