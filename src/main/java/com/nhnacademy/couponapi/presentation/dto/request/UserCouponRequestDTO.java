package com.nhnacademy.couponapi.presentation.dto.request;

import java.util.Date;

public record UserCouponRequestDTO(
        Long userId,
        Long couponId,
        Date userCouponUsedAt
) {}
