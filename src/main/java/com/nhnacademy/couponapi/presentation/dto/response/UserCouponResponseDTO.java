package com.nhnacademy.couponapi.presentation.dto.response;

import java.util.Date;

public record UserCouponResponseDTO(
        Long userCouponId,
        Long userId,
        Long couponId,
        Date userCouponUsedAt
) {}
