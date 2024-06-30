package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private String couponName;
    private String couponCode;
    private Date couponExpiredAt;
    private Date couponCreatedAt;
    private Date validFrom;
    private Date validTo;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @Builder
    public Coupon(Long couponId, String couponName, String couponCode, Date couponExpiredAt, Date couponCreatedAt, Date validFrom, Date validTo, CouponPolicy couponPolicy) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.couponExpiredAt = couponExpiredAt;
        this.couponCreatedAt = couponCreatedAt;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.couponPolicy = couponPolicy;
    }
}