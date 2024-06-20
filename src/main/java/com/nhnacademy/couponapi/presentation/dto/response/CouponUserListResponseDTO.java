package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

/*
    orders/checkout에서 쿠폰 목록보기
 */
@Builder
public record CouponUserListResponseDTO(
        Long userCouponId,
        Long userId,
        Long couponId,
        String couponName,
        String couponCode,
        BigDecimal couponPolicyDiscountValue,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        Date couponCreatedAt,
        Date couponExpiredAt,
        Date userCouponUsedAt
) {}
