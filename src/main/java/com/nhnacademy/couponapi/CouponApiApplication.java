package com.nhnacademy.couponapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.nhnacademy.couponapi.application.adapter")
@SpringBootApplication
public class CouponApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApiApplication.class, args);
    }

}
