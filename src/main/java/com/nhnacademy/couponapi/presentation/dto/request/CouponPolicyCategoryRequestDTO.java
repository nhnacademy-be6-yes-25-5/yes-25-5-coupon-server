package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record CouponPolicyCategoryRequestDTO(

        @NotNull(message = "Coupon policy ID cannot be null")
        Long couponPolicyId,

        @NotNull(message = "Category ID cannot be null")
        Long categoryId
) {}
