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

    @Builder
    public UserCoupon(Long userCouponId, Long userId, Coupon coupon, Date userCouponUsedAt) {
        this.userCouponId = userCouponId;
        this.userId = userId;
        this.coupon = coupon;
        this.userCouponUsedAt = userCouponUsedAt;
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
}
