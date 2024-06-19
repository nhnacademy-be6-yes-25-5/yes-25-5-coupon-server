package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<CouponPolicyCategoryResponseDTO>> findAll() {
        List<CouponPolicyCategoryResponseDTO> couponPolicyCategories = couponPolicyCategoryService.findAllCouponPolicyCategories();
        return ResponseEntity.ok(couponPolicyCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyCategoryResponseDTO> find(@PathVariable Long id) {
        CouponPolicyCategoryResponseDTO couponPolicyCategory = couponPolicyCategoryService.findCouponPolicyCategoryById(id);
        return ResponseEntity.ok(couponPolicyCategory);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyCategoryResponseDTO> create(@RequestBody @Valid CouponPolicyCategoryRequestDTO requestDTO) {
        CouponPolicyCategoryResponseDTO createdCouponPolicyCategory = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyCategoryResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
        CouponPolicyCategoryResponseDTO updatedCouponPolicyCategory = couponPolicyCategoryService.updateCouponPolicyCategory(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponPolicyCategoryService.deleteCouponPolicyCategory(id);
        return ResponseEntity.noContent().build();
    }
}
