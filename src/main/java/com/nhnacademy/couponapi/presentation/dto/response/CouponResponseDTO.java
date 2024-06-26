package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import lombok.Builder;

import java.util.Date;

@Builder
public record CouponResponseDTO(
        Long couponId,
        String couponName,
        String couponCode,
        Date couponExpiredAt,
        Date couponCreatedAt,
        Long couponPolicyId) {

    public static CouponResponseDTO fromEntity(Coupon coupon) {
        return new CouponResponseDTO(
                coupon.getCouponId(),
                coupon.getCouponName(),
                coupon.getCouponCode(),
                coupon.getCouponExpiredAt(),
                coupon.getCouponCreatedAt(),
                coupon.getCouponPolicy().getCouponPolicyId()
        );
    }
}