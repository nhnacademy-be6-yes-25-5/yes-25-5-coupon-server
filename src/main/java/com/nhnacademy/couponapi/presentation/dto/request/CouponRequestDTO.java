package com.nhnacademy.couponapi.presentation.dto.request;

import java.util.Date;

public record CouponRequestDTO(
        String couponName,
        String couponCode,
        Date couponExpiredAt,
        Long couponPolicyId
) {}
