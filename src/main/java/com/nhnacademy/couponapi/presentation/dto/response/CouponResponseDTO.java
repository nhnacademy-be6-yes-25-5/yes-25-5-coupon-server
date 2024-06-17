package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record CouponResponseDTO(
        Long couponId,
        String couponName,
        String couponCode,
        Date couponExpiredAt,
        Date couponCreatedAt,
        Long couponPolicyId
) {}
