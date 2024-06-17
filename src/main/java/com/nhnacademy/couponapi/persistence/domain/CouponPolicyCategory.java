package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_category")
public class CouponPolicyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyCategoryId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Builder
    public CouponPolicyCategory(Long couponPolicyCategoryId, CouponPolicy couponPolicy, Long categoryId) {
        this.couponPolicyCategoryId = couponPolicyCategoryId;
        this.couponPolicy = couponPolicy;
        this.categoryId = categoryId;
    }

    public void setCouponPolicy(CouponPolicy couponPolicy) {
        this.couponPolicy = couponPolicy;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
