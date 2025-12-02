package com.clinic.pharmacy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping
    public String testEndpoint() {
        log.info("Test endpoint accessed");
        return "Test endpoint is working!";
    }
}
