package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping
    public List<CouponResponseDTO> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/{id}")
    public CouponResponseDTO getCouponById(@PathVariable Long id) {
        return couponService.getCouponById(id);
    }

    @PostMapping
    public CouponResponseDTO createCoupon(@RequestBody CouponRequestDTO couponRequestDTO) {
        return couponService.createCoupon(couponRequestDTO);
    }

    @PutMapping("/{id}")
    public CouponResponseDTO updateCoupon(@PathVariable Long id, @RequestBody CouponRequestDTO couponRequestDTO) {
        return couponService.updateCoupon(id, couponRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
    }
}
