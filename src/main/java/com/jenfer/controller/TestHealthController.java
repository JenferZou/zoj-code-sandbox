package com.jenfer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TestHealthController {

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

}
