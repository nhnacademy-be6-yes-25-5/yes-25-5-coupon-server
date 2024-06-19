package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> findAll() {
        List<CouponResponseDTO> coupons = couponService.findAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> find(@PathVariable Long id) {
        CouponResponseDTO coupon = couponService.findCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@RequestBody @Valid CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO createdCoupon = couponService.createCoupon(couponRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO updatedCoupon = couponService.updateCoupon(id, couponRequestDTO);
        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
