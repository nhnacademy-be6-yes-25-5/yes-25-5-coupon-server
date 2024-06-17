package com.nhnacademy.couponapi.presentation.dto.response;

import java.util.Date;

public record CouponResponseDTO(
        Long couponId,
        String couponName,
        String couponCode,
        Date couponExpiredAt,
        Date couponCreatedAt,
        Long couponPolicyId
) {}
