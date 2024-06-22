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
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private Date userCouponUsedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status")
    private CouponStatus couponStatus;

    @Column(name = "coupon_type")
    private String couponType;

    @Builder
    public UserCoupon(Long userCouponId, Long userId, Coupon coupon, Date userCouponUsedAt, CouponStatus couponStatus, String couponType) {
        this.userCouponId = userCouponId;
        this.userId = userId;
        this.coupon = coupon;
        this.userCouponUsedAt = userCouponUsedAt;
        this.couponStatus = couponStatus;
        this.couponType = couponType;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserCouponUsedAt(Date userCouponUsedAt) {
        this.userCouponUsedAt = userCouponUsedAt;
    }

    public void setCouponStatus(CouponStatus couponStatus) {
        this.couponStatus = couponStatus;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public enum CouponStatus {
        ACTIVE,
        USED,
        EXPIRED
    }
}