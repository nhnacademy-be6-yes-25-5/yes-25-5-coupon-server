package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.presentation.dto.request.CreateCouponRequest;
import com.nhnacademy.couponapi.presentation.dto.request.UseCouponRequest;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponse;
import com.nhnacademy.couponapi.application.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public CouponResponse createCoupon(@RequestBody CreateCouponRequest request) {
        return couponService.createCoupon(request);
    }

    @GetMapping("/{userId}")
    public List<CouponResponse> getCouponsByUser(@PathVariable Long userId) {
        return couponService.getCouponsByUser(userId);
    }

    @PostMapping("/use")
    public void useCoupon(@RequestBody UseCouponRequest request) {
        couponService.useCoupon(request);
    }
}
