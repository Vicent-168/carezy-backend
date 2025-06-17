package com.shdata.health.carezy;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableMPP
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.shdata.health.*"})
@ComponentScan(basePackages = {"com.shdata.health.*"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
