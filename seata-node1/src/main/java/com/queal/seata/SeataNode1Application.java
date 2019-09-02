package com.queal.seata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.queal.seata.mybatis.dao")
public class SeataNode1Application {

    public static void main(String[] args) {
        SpringApplication.run(SeataNode1Application.class, args);
    }

}
