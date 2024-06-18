package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-categories")
public class CouponPolicyCategoryController {

    private final CouponPolicyCategoryService couponPolicyCategoryService;

    @GetMapping
    public ResponseEntity<List<CouponPolicyCategoryResponseDTO>> getAllCouponPolicyCategories() {
        List<CouponPolicyCategoryResponseDTO> couponPolicyCategories = couponPolicyCategoryService.getAllCouponPolicyCategories();
        return new ResponseEntity<>(couponPolicyCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyCategoryResponseDTO> getCouponPolicyCategoryById(@PathVariable Long id) {
        CouponPolicyCategoryResponseDTO couponPolicyCategory = couponPolicyCategoryService.getCouponPolicyCategoryById(id);
        return new ResponseEntity<>(couponPolicyCategory, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyCategoryResponseDTO> createCouponPolicyCategory(@RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
        CouponPolicyCategoryResponseDTO createdCouponPolicyCategory = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
        return new ResponseEntity<>(createdCouponPolicyCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyCategoryResponseDTO> updateCouponPolicyCategory(@PathVariable Long id, @RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
        CouponPolicyCategoryResponseDTO updatedCouponPolicyCategory = couponPolicyCategoryService.updateCouponPolicyCategory(id, requestDTO);
        return new ResponseEntity<>(updatedCouponPolicyCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicyCategory(@PathVariable Long id) {
        couponPolicyCategoryService.deleteCouponPolicyCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
