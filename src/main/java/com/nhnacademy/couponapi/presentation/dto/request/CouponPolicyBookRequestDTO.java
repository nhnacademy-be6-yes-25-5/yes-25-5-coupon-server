package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CouponPolicyBookRequestDTO(

        @NotBlank(message = "쿠폰 정책명은 비어 있을 수 없습니다.")
        String couponPolicyName,

        BigDecimal couponPolicyDiscountValue,

        BigDecimal couponPolicyRate,

        @NotNull(message = "최소 주문 금액은 비어 있을 수 없습니다.")
        BigDecimal couponPolicyMinOrderAmount,

        @NotNull(message = "최대 할인 금액은 비어 있을 수 없습니다.")
        BigDecimal couponPolicyMaxAmount,

        boolean couponPolicyDiscountType,

        @NotBlank(message = "책 이름은 비어 있을 수 없습니다.")
        String bookName,

        @NotNull(message = "책 ID는 비어 있을 수 없습니다.")
        Long bookId

) {}