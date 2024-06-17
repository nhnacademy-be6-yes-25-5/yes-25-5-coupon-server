package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-books")
public class CouponPolicyBookController {

    private final CouponPolicyBookService couponPolicyBookService;

    @GetMapping
    public List<CouponPolicyBookResponseDTO> getAllCouponPolicyBooks() {
        return couponPolicyBookService.getAllCouponPolicyBooks();
    }

    @GetMapping("/{id}")
    public CouponPolicyBookResponseDTO getCouponPolicyBookById(@PathVariable Long id) {
        return couponPolicyBookService.getCouponPolicyBookById(id);
    }

    @PostMapping
    public CouponPolicyBookResponseDTO createCouponPolicyBook(@RequestBody CouponPolicyBookRequestDTO requestDTO) {
        return couponPolicyBookService.createCouponPolicyBook(requestDTO);
    }

    @PutMapping("/{id}")
    public CouponPolicyBookResponseDTO updateCouponPolicyBook(@PathVariable Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
        return couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCouponPolicyBook(@PathVariable Long id) {
        couponPolicyBookService.deleteCouponPolicyBook(id);
    }
}
