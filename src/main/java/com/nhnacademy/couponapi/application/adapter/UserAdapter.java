package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-api", url = "http://user-api")
public interface UserAdapter {

    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

}
