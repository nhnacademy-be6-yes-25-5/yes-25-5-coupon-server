package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons() {
        List<CouponResponseDTO> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long id) {
        CouponResponseDTO coupon = couponService.getCouponById(id);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(@RequestBody CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO createdCoupon = couponService.createCoupon(couponRequestDTO);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(@PathVariable Long id, @RequestBody CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO updatedCoupon = couponService.updateCoupon(id, couponRequestDTO);
        return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
