package com.nhnacademy.couponapi.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CouponRequestDTO(

        @NotBlank(message = "쿠폰 이름은 비어 있을 수 없습니다.")
        String couponName,

        @NotBlank(message = "쿠폰 코드는 비어 있을 수 없습니다.")
        String couponCode,

        @NotNull(message = "쿠폰 만료 날짜는 비어 있을 수 없습니다.")
        Date couponExpiredAt,

        @NotNull(message = "쿠폰 정책 ID는 비어 있을 수 없습니다.")
        Long couponPolicyId

) {}