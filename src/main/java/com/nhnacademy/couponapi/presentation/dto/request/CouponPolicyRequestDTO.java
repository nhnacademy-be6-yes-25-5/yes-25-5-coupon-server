package com.nhnacademy.couponapi.presentation.dto.request;

import java.math.BigDecimal;
import java.util.Date;

public record CouponPolicyRequestDTO(
        String couponPolicyName,
        BigDecimal couponPolicyDiscountValue,
        Date couponPolicyCreatedAt,
        Date couponPolicyUpdatedAt,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        boolean couponPolicyDiscountType
) {}
