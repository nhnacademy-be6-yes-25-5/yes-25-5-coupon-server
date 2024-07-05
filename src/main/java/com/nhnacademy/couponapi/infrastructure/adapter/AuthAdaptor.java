package com.nhnacademy.couponapi.infrastructure.adapter;


import com.nhnacademy.couponapi.presentation.dto.response.JwtAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authAdaptor", url = "${api.auth}/auth")
public interface AuthAdaptor {

    @GetMapping("/info")
    JwtAuthResponse getUserInfoByUUID(@RequestParam String uuid);
}
