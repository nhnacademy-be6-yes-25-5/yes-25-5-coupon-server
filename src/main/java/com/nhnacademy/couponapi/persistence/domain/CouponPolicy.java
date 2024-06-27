package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(nullable = false)
    private String couponPolicyName;

    private BigDecimal couponPolicyDiscountValue;

    private BigDecimal couponPolicyRate;

    @Column(nullable = false)
    private BigDecimal couponPolicyMinOrderAmount;

    @Column(nullable = false)
    private BigDecimal couponPolicyMaxAmount;

    private boolean couponPolicyDiscountType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date couponPolicyCreatedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date couponPolicyUpdatedAt;

    @OneToMany(mappedBy = "couponPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
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