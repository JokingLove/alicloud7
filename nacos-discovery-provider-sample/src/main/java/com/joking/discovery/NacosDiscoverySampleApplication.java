package com.joking.discovery;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.joking.discovery.sentinel.MyBlockExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoverySampleApplication {

    @Value("${hello}")
    private String hello;

    @NacosInjected
    private NamingService namingService;

    @NacosInjected
    private ConfigService configService;

    @SentinelResource()
    @GetMapping("/hello/{message}")
    public String helloMessage(@PathVariable("message") String message) {
        if("0".equals(message)) {
            throw new RuntimeException("异常");
        }
        return hello + ":" + message;
    }

    @SentinelResource(fallback = "fallback" )
    @GetMapping("/config/hello")
    public String config() {
        return hello;
    }

    public String fallback() {
        return "访问太快了";
    }

    @GetMapping("/discovery/get/{serviceName}")
    public List<Instance> instanceList(@PathVariable("serviceName") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoverySampleApplication.class, args);
    }

}
