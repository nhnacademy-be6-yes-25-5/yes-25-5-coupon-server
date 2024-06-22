//package com.nhnacademy.couponapi.application.adapter;
//
//import com.nhnacademy.couponapi.presentation.dto.response.TagResponseDTO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "tag-api", url = "http://tag-api")
//public interface TagAdapter {
//
//    @GetMapping("/tags/{id}")
//    TagResponseDTO getTagById(@PathVariable("id") Long id);
//
//}
