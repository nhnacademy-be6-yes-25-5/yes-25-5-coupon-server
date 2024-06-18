package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.util.Date;

@Builder
public record UserCouponResponseDTO(
        Long userCouponId,
        Long userId,
        Long couponId,
        Date userCouponUsedAt,
        Date userCouponCreatedAt
) {}
