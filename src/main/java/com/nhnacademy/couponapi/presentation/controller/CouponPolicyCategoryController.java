package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon-policy-categories")
public class CouponPolicyCategoryController {
    @Autowired
    private CouponPolicyCategoryService couponPolicyCategoryService;

    @GetMapping
    public List<CouponPolicyCategoryResponseDTO> getAllCouponPolicyCategories() {
        return couponPolicyCategoryService.getAllCouponPolicyCategories();
    }

    @GetMapping("/{id}")
    public CouponPolicyCategoryResponseDTO getCouponPolicyCategoryById(@PathVariable Long id) {
        return couponPolicyCategoryService.getCouponPolicyCategoryById(id);
    }

    @PostMapping
    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(@RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
        return couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
    }

    @PutMapping("/{id}")
    public CouponPolicyCategoryResponseDTO updateCouponPolicyCategory(@PathVariable Long id, @RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
        return couponPolicyCategoryService.updateCouponPolicyCategory(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCouponPolicyCategory(@PathVariable Long id) {
        couponPolicyCategoryService.deleteCouponPolicyCategory(id);
    }
}
