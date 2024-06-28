package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_category")
@SuperBuilder(toBuilder = true)
public class CouponPolicyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyCategoryId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private Long categoryName;

    @Builder
    public CouponPolicyCategory(Long couponPolicyCategoryId, CouponPolicy couponPolicy, Long categoryId) {
        this.couponPolicyCategoryId = couponPolicyCategoryId;
        this.couponPolicy = couponPolicy;
        this.categoryId = categoryId;
    }

    public CouponPolicyCategory updateCouponPolicy(CouponPolicy couponPolicy) {
        return this.toBuilder()
                .couponPolicy(couponPolicy)
                .build();
    }

    public CouponPolicyCategory updateCategoryId(Long categoryId) {
        return this.toBuilder()
                .categoryId(categoryId)
                .build();
    }
}