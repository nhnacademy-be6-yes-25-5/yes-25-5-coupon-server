package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UserCouponRequestDTO(

        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Coupon ID cannot be null")
        Long couponId,

        Date userCouponUsedAt
) {}
