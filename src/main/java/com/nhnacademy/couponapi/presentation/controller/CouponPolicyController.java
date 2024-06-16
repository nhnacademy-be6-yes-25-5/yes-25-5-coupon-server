package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.presentation.dto.request.CreateCouponPolicyRequest;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponse;
import com.nhnacademy.couponapi.service.CouponPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon-policies")
public class CouponPolicyController {

    @Autowired
    private CouponPolicyService couponPolicyService;

    @PostMapping
    public CouponPolicyResponse createCouponPolicy(@RequestBody CreateCouponPolicyRequest request) {
        return couponPolicyService.createCouponPolicy(request);
    }
}
