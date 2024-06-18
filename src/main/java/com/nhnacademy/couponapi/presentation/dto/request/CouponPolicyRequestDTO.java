package com.nhnacademy.couponapi.presentation.dto.request;

import java.math.BigDecimal;

public record CouponPolicyRequestDTO(
        String couponPolicyName,
        BigDecimal couponPolicyDiscountValue,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        boolean couponPolicyDiscountType
) {}
