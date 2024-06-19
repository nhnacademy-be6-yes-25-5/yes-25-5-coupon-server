package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CouponPolicyRequestDTO(

        @NotEmpty(message = "Coupon policy name cannot be null")
        String couponPolicyName,

        @NotNull(message = "Coupon policy discount value cannot be null")
        BigDecimal couponPolicyDiscountValue,

        @NotNull(message = "Coupon policy rate cannot be null")
        BigDecimal couponPolicyRate,

        @NotNull(message = "Coupon policy minimum order amount cannot be null")
        BigDecimal couponPolicyMinOrderAmount,

        @NotNull(message = "Coupon policy maximum amount cannot be null")
        BigDecimal couponPolicyMaxAmount,

        boolean couponPolicyDiscountType
) {}
