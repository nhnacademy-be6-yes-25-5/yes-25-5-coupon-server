package com.nhnacademy.couponapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages = "com.nhnacademy.couponapi.infrastructure.adapter")
@SpringBootApplication
public class CouponApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApiApplication.class, args);
    }

}
