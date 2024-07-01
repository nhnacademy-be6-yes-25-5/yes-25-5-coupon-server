package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CouponPolicyBookRequestDTO(
        @NotEmpty(message = "Coupon policy name cannot be null")
        String couponPolicyName,

        BigDecimal couponPolicyDiscountValue,

        BigDecimal couponPolicyRate,

        @NotNull(message = "Coupon policy minimum order amount cannot be null")
        BigDecimal couponPolicyMinOrderAmount,

        @NotNull(message = "Coupon policy maximum amount cannot be null")
        BigDecimal couponPolicyMaxAmount,

        @NotNull(message = "Coupon policy discount type cannot be null")
        boolean couponPolicyDiscountType,

        @NotEmpty(message = "Book name cannot be null")
        String bookName,

        @NotNull(message = "Book ID cannot be null")
        Long bookId
) {}