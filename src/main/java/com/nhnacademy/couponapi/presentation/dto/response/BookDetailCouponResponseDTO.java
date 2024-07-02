package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class BookDetailCouponResponseDTO {
    private final Long couponId;
    private final String couponName;
    private final Date couponExpiredAt;
    private final String couponPolicyName;
    private final BigDecimal couponPolicyDiscountValue;
    private final BigDecimal couponPolicyRate;

    public static BookDetailCouponResponseDTO fromEntity(Coupon coupon) {
        CouponPolicy policy = coupon.getCouponPolicy();
        return BookDetailCouponResponseDTO.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .couponPolicyName(policy != null ? policy.getCouponPolicyName() : null)
                .couponPolicyDiscountValue(policy != null ? policy.getCouponPolicyDiscountValue() : null)
                .couponPolicyRate(policy != null ? policy.getCouponPolicyRate() : null)
                .build();
    }
}