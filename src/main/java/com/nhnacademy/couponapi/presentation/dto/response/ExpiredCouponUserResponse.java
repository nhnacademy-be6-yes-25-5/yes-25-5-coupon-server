package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record ExpiredCouponUserResponse(Date couponExpiredAt) {
}
