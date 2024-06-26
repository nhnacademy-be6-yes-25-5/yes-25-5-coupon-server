package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
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
        Date userCouponUsedAt) {

    public static CouponUserListResponseDTO fromEntity(UserCoupon userCoupon) {
        return new CouponUserListResponseDTO(
                userCoupon.getUserCouponId(),
                userCoupon.getUserId(),
                userCoupon.getCoupon().getCouponId(),
                userCoupon.getCoupon().getCouponName(),
                userCoupon.getCoupon().getCouponCode(),
                userCoupon.getCoupon().getCouponPolicy().getCouponPolicyDiscountValue(),
                userCoupon.getCoupon().getCouponPolicy().getCouponPolicyRate(),
                userCoupon.getCoupon().getCouponPolicy().getCouponPolicyMinOrderAmount(),
                userCoupon.getCoupon().getCouponPolicy().getCouponPolicyMaxAmount(),
                userCoupon.getCoupon().getCouponCreatedAt(),
                userCoupon.getCoupon().getCouponExpiredAt(),
                userCoupon.getUserCouponUsedAt()
        );
    }
}