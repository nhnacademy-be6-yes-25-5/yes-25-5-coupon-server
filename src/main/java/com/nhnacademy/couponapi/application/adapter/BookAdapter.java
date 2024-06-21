package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.BookResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookAdapter", url = "http://133.186.153.195:8085")
public interface BookAdapter {

    @GetMapping("/books/{id}")
    BookResponseDTO getBookById(@PathVariable("id") Long id);

}
