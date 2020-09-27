package com.joking.consumer.controller;

import com.joking.consumer.service.feign.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/provider/{message}")
    public String test(@PathVariable("message") String message) {
        return providerService.hello(message);
    }
}
