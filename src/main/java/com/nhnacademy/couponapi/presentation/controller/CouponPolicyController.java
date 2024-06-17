package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policies")
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    @GetMapping
    public List<CouponPolicyResponseDTO> getAllCouponPolicies() {
        return couponPolicyService.getAllCouponPolicies();
    }

    @GetMapping("/{id}")
    public CouponPolicyResponseDTO getCouponPolicyById(@PathVariable Long id) {
        return couponPolicyService.getCouponPolicyById(id);
    }

    @PostMapping
    public CouponPolicyResponseDTO createCouponPolicy(@RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        return couponPolicyService.createCouponPolicy(couponPolicyRequestDTO);
    }

    @PutMapping("/{id}")
    public CouponPolicyResponseDTO updateCouponPolicy(@PathVariable Long id, @RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
        return couponPolicyService.updateCouponPolicy(id, couponPolicyRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCouponPolicy(@PathVariable Long id) {
        couponPolicyService.deleteCouponPolicy(id);
    }
}
