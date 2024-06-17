package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_tag")
public class CouponPolicyTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyTagId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "tag_id")
    private Long tagId;

    @Builder
    public CouponPolicyTag(Long couponPolicyTagId, CouponPolicy couponPolicy, Long tagId) {
        this.couponPolicyTagId = couponPolicyTagId;
        this.couponPolicy = couponPolicy;
        this.tagId = tagId;
    }

    public void setCouponPolicy(CouponPolicy couponPolicy) {
        this.couponPolicy = couponPolicy;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}
