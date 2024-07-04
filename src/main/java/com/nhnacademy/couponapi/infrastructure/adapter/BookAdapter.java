package com.nhnacademy.couponapi.infrastructure.adapter;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "bookAdapter", url = "${eureka.gateway}/books")
public interface BookAdapter {
}
