package com.joking.consumer.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("nacos-discovery-provider-sample")
public interface ProviderService {

    @GetMapping("/hello/{message}")
    String hello(@PathVariable("message") String message);
}
