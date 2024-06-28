package com.nhnacademy.couponapi.presentation.dto.request;

import com.nhnacademy.couponapi.validation.EitherOr;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@EitherOr
public record CouponPolicyCategoryRequestDTO(

        @NotEmpty(message = "Category name cannot be null")
        String categoryName,

        @NotEmpty(message = "Coupon policy name cannot be null")
        String couponPolicyName,

        @Nullable
        BigDecimal couponPolicyDiscountValue,

        @Nullable
        BigDecimal couponPolicyRate,

        @NotNull(message = "Coupon policy minimum order amount cannot be null")
        BigDecimal couponPolicyMinOrderAmount,

        @NotNull(message = "Coupon policy maximum amount cannot be null")
        BigDecimal couponPolicyMaxAmount,

        boolean couponPolicyDiscountType
) {}