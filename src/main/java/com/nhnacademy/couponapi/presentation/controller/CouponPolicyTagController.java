package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-tags")
public class CouponPolicyTagController {

    private final CouponPolicyTagService couponPolicyTagService;

    @GetMapping
    public List<CouponPolicyTagResponseDTO> getAllCouponPolicyTags() {
        return couponPolicyTagService.getAllCouponPolicyTags();
    }

    @GetMapping("/{id}")
    public CouponPolicyTagResponseDTO getCouponPolicyTagById(@PathVariable Long id) {
        return couponPolicyTagService.getCouponPolicyTagById(id);
    }

    @PostMapping
    public CouponPolicyTagResponseDTO createCouponPolicyTag(@RequestBody CouponPolicyTagRequestDTO requestDTO) {
        return couponPolicyTagService.createCouponPolicyTag(requestDTO);
    }

    @PutMapping("/{id}")
    public CouponPolicyTagResponseDTO updateCouponPolicyTag(@PathVariable Long id, @RequestBody CouponPolicyTagRequestDTO requestDTO) {
        return couponPolicyTagService.updateCouponPolicyTag(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCouponPolicyTag(@PathVariable Long id) {
        couponPolicyTagService.deleteCouponPolicyTag(id);
    }
}
