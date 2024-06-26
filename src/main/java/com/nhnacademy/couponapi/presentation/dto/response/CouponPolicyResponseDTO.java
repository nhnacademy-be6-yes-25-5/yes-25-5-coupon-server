package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record CouponPolicyResponseDTO(
        Long couponPolicyId,
        String couponPolicyName,
        BigDecimal couponPolicyDiscountValue,
        Date couponPolicyCreatedAt,
        Date couponPolicyUpdatedAt,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        boolean couponPolicyDiscountType) {

    public static CouponPolicyResponseDTO fromEntity(CouponPolicy couponPolicy) {
        return new CouponPolicyResponseDTO(
                couponPolicy.getCouponPolicyId(),
                couponPolicy.getCouponPolicyName(),
                couponPolicy.getCouponPolicyDiscountValue(),
                couponPolicy.getCouponPolicyCreatedAt(),
                couponPolicy.getCouponPolicyUpdatedAt(),
                couponPolicy.getCouponPolicyRate(),
                couponPolicy.getCouponPolicyMinOrderAmount(),
                couponPolicy.getCouponPolicyMaxAmount(),
                couponPolicy.isCouponPolicyDiscountType()
        );
    }
}
