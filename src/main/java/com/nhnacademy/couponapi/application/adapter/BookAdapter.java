package com.nhnacademy.couponapi.application.adapter;

import com.nhnacademy.couponapi.presentation.dto.response.BookResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookAdaptor", url = "${api.books-users}/books")
public interface BookAdapter {

    @GetMapping("/{id}")
    BookResponseDTO findByBookId(@PathVariable("id") Long id);

}
