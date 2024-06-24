package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "userAdaptor", url = "${api.books-users}/users")
public interface UserAdapter {

    @GetMapping("/users/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long id);

    @GetMapping("/users")
    List<UserResponseDTO> findAllUsers();
}