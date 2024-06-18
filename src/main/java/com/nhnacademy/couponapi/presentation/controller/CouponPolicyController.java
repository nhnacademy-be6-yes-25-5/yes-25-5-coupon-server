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
        return new ResponseEntity<>(couponPolicies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> getCouponPolicyById(@PathVariable Long id) {
        CouponPolicyResponseDTO couponPolicy = couponPolicyService.getCouponPolicyById(id);
        return new ResponseEntity<>(couponPolicy, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyResponseDTO> createCouponPolicy(@RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicyResponseDTO createdCouponPolicy = couponPolicyService.createCouponPolicy(couponPolicyRequestDTO);
        return new ResponseEntity<>(createdCouponPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> updateCouponPolicy(@PathVariable Long id, @RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicyResponseDTO updatedCouponPolicy = couponPolicyService.updateCouponPolicy(id, couponPolicyRequestDTO);
        return new ResponseEntity<>(updatedCouponPolicy, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicy(@PathVariable Long id) {
        couponPolicyService.deleteCouponPolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
