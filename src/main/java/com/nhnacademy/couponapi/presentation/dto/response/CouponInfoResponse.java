package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
public record CouponInfoResponse(Long couponId,
                                 String couponName,
                                 BigDecimal couponMinAmount,
                                 BigDecimal couponMaxAmount,
                                 BigDecimal couponDiscountAmount,
                                 BigDecimal couponDiscountRate,
                                 Date couponCreatedAt,
                                 String couponCode,
                                 List<Long> bookIds,
                                 List<Long> categoryIds,
                                 Boolean couponDiscountType) {

    public List<Long> getBookIds() {
        return bookIds;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }
}