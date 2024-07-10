package com.nhnacademy.couponapi.presentation.dto.request;

import com.nhnacademy.couponapi.validation.EitherOr;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@EitherOr
public record CouponPolicyRequestDTO(

        @NotBlank(message = "쿠폰 정책명은 비어 있을 수 없습니다.")
        String couponPolicyName,

        @Nullable
        BigDecimal couponPolicyDiscountValue,

        @Nullable
        BigDecimal couponPolicyRate,

        @NotNull(message = "최소 주문 금액은 비어 있을 수 없습니다.")
        BigDecimal couponPolicyMinOrderAmount,

        @NotNull(message = "최대 할인 금액은 비어 있을 수 없습니다.")
        BigDecimal couponPolicyMaxAmount,

        boolean couponPolicyDiscountType

) {}