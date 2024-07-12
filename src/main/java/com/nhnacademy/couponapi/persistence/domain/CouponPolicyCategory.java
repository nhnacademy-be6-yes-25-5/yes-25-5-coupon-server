package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_category", indexes = {
        @Index(name = "idx_category_id", columnList = "category_id"),
        @Index(name = "idx_coupon_policy", columnList = "coupon_policy_id")
})
public class CouponPolicyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyCategoryId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Builder
    public CouponPolicyCategory(Long couponPolicyCategoryId, CouponPolicy couponPolicy, Long categoryId, String categoryName) {
        this.couponPolicyCategoryId = couponPolicyCategoryId;
        this.couponPolicy = couponPolicy;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}