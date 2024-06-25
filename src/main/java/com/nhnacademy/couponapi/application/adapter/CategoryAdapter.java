package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.CategoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookAdapter2", url = "http://133.186.153.195:8085/categories")
public interface CategoryAdapter {

    @GetMapping("/{id}")
    CategoryResponseDTO getCategoryById(@PathVariable("id") Long id);

}
