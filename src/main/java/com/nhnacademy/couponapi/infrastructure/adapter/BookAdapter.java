package com.nhnacademy.couponapi.infrastructure.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "bookAdapter", url = "${eureka.gateway}/books")
public interface BookAdapter {

}
