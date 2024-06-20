package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record CouponPolicyTagRequestDTO(

        @NotNull(message = "Coupon policy ID cannot be null")
        Long couponPolicyId,

        @NotNull(message = "Tag ID cannot be null")
        Long tagId
) {}
