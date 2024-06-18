package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-tags")
public class CouponPolicyTagController {

    private final CouponPolicyTagService couponPolicyTagService;

    @GetMapping
    public ResponseEntity<List<CouponPolicyTagResponseDTO>> getAllCouponPolicyTags() {
        List<CouponPolicyTagResponseDTO> couponPolicyTags = couponPolicyTagService.getAllCouponPolicyTags();
        return ResponseEntity.ok(couponPolicyTags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> getCouponPolicyTagById(@PathVariable Long id) {
        CouponPolicyTagResponseDTO couponPolicyTag = couponPolicyTagService.getCouponPolicyTagById(id);
        return ResponseEntity.ok(couponPolicyTag);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyTagResponseDTO> createCouponPolicyTag(@RequestBody CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO createdCouponPolicyTag = couponPolicyTagService.createCouponPolicyTag(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> updateCouponPolicyTag(@PathVariable Long id, @RequestBody CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO updatedCouponPolicyTag = couponPolicyTagService.updateCouponPolicyTag(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicyTag(@PathVariable Long id) {
        couponPolicyTagService.deleteCouponPolicyTag(id);
        return ResponseEntity.noContent().build();
    }
}
