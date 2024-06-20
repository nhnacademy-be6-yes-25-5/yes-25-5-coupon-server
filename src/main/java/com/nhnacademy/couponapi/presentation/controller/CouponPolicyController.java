package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-policy")
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @GetMapping
    public ResponseEntity<List<CouponPolicyResponseDTO>> findAll() {
        List<CouponPolicyResponseDTO> couponPolicies = couponPolicyService.findAllCouponPolicies();
        return ResponseEntity.ok(couponPolicies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> find(@PathVariable Long id) {
        CouponPolicyResponseDTO couponPolicy = couponPolicyService.findCouponPolicyById(id);
        return ResponseEntity.ok(couponPolicy);
    }

    @PostMapping("/coupon")
    public ResponseEntity<CouponPolicyResponseDTO> create(@RequestBody @Valid CouponPolicyRequestDTO couponPolicyRequestDTO) {
        log.info("Creating new coupon policy with request: {}", couponPolicyRequestDTO);
        CouponPolicyResponseDTO createdCouponPolicy = couponPolicyService.createCouponPolicy(couponPolicyRequestDTO);
        log.info("Created coupon policy: {}", createdCouponPolicy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicyResponseDTO updatedCouponPolicy = couponPolicyService.updateCouponPolicy(id, couponPolicyRequestDTO);
        return ResponseEntity.ok(updatedCouponPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponPolicyService.deleteCouponPolicy(id);
        return ResponseEntity.noContent().build();
    }
}
