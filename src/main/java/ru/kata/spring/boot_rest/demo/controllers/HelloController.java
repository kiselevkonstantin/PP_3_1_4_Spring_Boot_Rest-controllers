package ru.kata.spring.boot_rest.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
}
