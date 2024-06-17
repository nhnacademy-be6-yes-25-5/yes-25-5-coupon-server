package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyId;
    private String couponPolicyName;
    private BigDecimal couponPolicyDiscountValue;
    private Date couponPolicyCreatedAt;
    private Date couponPolicyUpdatedAt;
    private BigDecimal couponPolicyRate;
    private BigDecimal couponPolicyMinOrderAmount;
    private BigDecimal couponPolicyMaxAmount;
    private boolean couponPolicyDiscountType;

    @OneToMany(mappedBy = "couponPolicy")
    private List<Coupon> coupons;

    @Builder
    public CouponPolicy(Long couponPolicyId, String couponPolicyName, BigDecimal couponPolicyDiscountValue, Date couponPolicyCreatedAt,
                        Date couponPolicyUpdatedAt, BigDecimal couponPolicyRate, BigDecimal couponPolicyMinOrderAmount,
                        BigDecimal couponPolicyMaxAmount, boolean couponPolicyDiscountType, List<Coupon> coupons) {
        this.couponPolicyId = couponPolicyId;
        this.couponPolicyName = couponPolicyName;
        this.couponPolicyDiscountValue = couponPolicyDiscountValue;
        this.couponPolicyCreatedAt = couponPolicyCreatedAt;
        this.couponPolicyUpdatedAt = couponPolicyUpdatedAt;
        this.couponPolicyRate = couponPolicyRate;
        this.couponPolicyMinOrderAmount = couponPolicyMinOrderAmount;
        this.couponPolicyMaxAmount = couponPolicyMaxAmount;
        this.couponPolicyDiscountType = couponPolicyDiscountType;
        this.coupons = coupons;
    }
}
