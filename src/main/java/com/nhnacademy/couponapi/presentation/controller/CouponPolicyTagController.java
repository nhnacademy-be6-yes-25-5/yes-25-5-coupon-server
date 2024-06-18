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
        return new ResponseEntity<>(couponPolicyTags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> getCouponPolicyTagById(@PathVariable Long id) {
        CouponPolicyTagResponseDTO couponPolicyTag = couponPolicyTagService.getCouponPolicyTagById(id);
        return new ResponseEntity<>(couponPolicyTag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyTagResponseDTO> createCouponPolicyTag(@RequestBody CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO createdCouponPolicyTag = couponPolicyTagService.createCouponPolicyTag(requestDTO);
        return new ResponseEntity<>(createdCouponPolicyTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> updateCouponPolicyTag(@PathVariable Long id, @RequestBody CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO updatedCouponPolicyTag = couponPolicyTagService.updateCouponPolicyTag(id, requestDTO);
        return new ResponseEntity<>(updatedCouponPolicyTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicyTag(@PathVariable Long id) {
        couponPolicyTagService.deleteCouponPolicyTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
