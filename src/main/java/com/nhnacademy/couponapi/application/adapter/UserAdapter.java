package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userAdaptor", url = "http://133.186.153.195:8085")
public interface UserAdapter {

    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

}
