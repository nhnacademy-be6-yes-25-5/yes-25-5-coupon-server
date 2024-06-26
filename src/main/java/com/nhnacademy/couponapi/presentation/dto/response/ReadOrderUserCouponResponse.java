package com.nhnacademy.couponapi.presentation.dto.response;

import java.math.BigDecimal;

public record ReadOrderUserCouponResponse(Long couponId, BigDecimal discountAmount) {
}