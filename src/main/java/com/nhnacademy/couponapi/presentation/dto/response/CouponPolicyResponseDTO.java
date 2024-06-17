package com.nhnacademy.couponapi.presentation.dto.response;

import java.math.BigDecimal;
import java.util.Date;

public record CouponPolicyResponseDTO(
        Long couponPolicyId,
        String couponPolicyName,
        BigDecimal couponPolicyDiscountValue,
        Date couponPolicyCreatedAt,
        Date couponPolicyUpdatedAt,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        boolean couponPolicyDiscountType
) {}
