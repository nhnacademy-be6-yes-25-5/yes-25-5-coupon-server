package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.CategoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BOOK-USER-SERVER")
public interface CategoryAdapter {

    @GetMapping("/categories/{id}")
    CategoryResponseDTO getCategoryById(@PathVariable("id") Long id);

}
