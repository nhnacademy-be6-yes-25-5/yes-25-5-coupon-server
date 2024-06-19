package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record CouponPolicyBookRequestDTO(

        @NotNull(message = "Coupon policy ID cannot be null")
        Long couponPolicyId,

        @NotNull(message = "Book Id cannot be null")
        Long bookId
) {}
