package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CouponRequestDTO(

        @NotEmpty(message = "Coupon name cannot be empty")
        String couponName,

        @NotEmpty(message = "Coupon code cannot be empty")
        String couponCode,

        @NotNull(message = "Coupon expired date cannot be null")
        Date couponExpiredAt,

        @NotNull(message = "Coupon policy ID cannot be null")
        Long couponPolicyId
) {}
