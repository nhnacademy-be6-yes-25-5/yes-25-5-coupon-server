package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policies")
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @GetMapping
    public ResponseEntity<List<CouponPolicyResponseDTO>> getAllCouponPolicies() {
        List<CouponPolicyResponseDTO> couponPolicies = couponPolicyService.getAllCouponPolicies();
        return ResponseEntity.ok(couponPolicies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> getCouponPolicyById(@PathVariable Long id) {
        CouponPolicyResponseDTO couponPolicy = couponPolicyService.getCouponPolicyById(id);
        return ResponseEntity.ok(couponPolicy);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyResponseDTO> createCouponPolicy(@RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicyResponseDTO createdCouponPolicy = couponPolicyService.createCouponPolicy(couponPolicyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> updateCouponPolicy(@PathVariable Long id, @RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicyResponseDTO updatedCouponPolicy = couponPolicyService.updateCouponPolicy(id, couponPolicyRequestDTO);
        return ResponseEntity.ok(updatedCouponPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicy(@PathVariable Long id) {
        couponPolicyService.deleteCouponPolicy(id);
        return ResponseEntity.noContent().build();
    }
}
