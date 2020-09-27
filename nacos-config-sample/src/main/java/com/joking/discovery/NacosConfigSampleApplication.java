package com.joking.discovery;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.Properties;

@RefreshScope
@RestController
@SpringBootApplication
public class NacosConfigSampleApplication {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Value("${hello}")
    private String hello;

    @PostConstruct
    public void init() {
        System.out.println(hello);
    }

    @RequestMapping("/hello")
    public String hello() {
        return hello;
    }

    @Bean
    public ApplicationRunner run() throws NacosException {
        return args -> {
            String dataId = "nacos-config-sample";
            String group = "DEFAULT_GROUP";
            nacosConfigManager.getConfigService()
                    .addListener(dataId, group, new AbstractListener() {
                        @Override
                        public void receiveConfigInfo(String configInfo) {
//                            System.out.println("[listener]" + configInfo);
                            try{

                                Yaml yaml = new Yaml();
//                                Properties properties = yaml.loadAs(configInfo, Properties.class);
                                Properties properties = new Properties();
                                properties.load(new StringReader(configInfo));
                                System.out.println(properties.get("hello"));
                                System.out.println(properties.get("user.name"));
                                System.out.println(properties.get("user.age"));
                            } catch (Exception e ) {
                                // nothing
                            }
                        }
                    });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigSampleApplication.class, args);
    }

}
