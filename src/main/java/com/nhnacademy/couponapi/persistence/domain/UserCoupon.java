package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicUpdate
@SuperBuilder(toBuilder = true)
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

    // Builder 패턴을 사용하여 필드 업데이트
    public UserCoupon updateCouponStatus(CouponStatus couponStatus) {
        return this.toBuilder()
                .couponStatus(couponStatus)
                .build();
    }

    public UserCoupon updateUserCouponUsedAt(Date userCouponUsedAt) {
        return this.toBuilder()
                .userCouponUsedAt(userCouponUsedAt)
                .build();
    }

    public UserCoupon updateUserId(Long userId) {
        return this.toBuilder()
                .userId(userId)
                .build();
    }

    public UserCoupon updateCouponType(String couponType) {
        return this.toBuilder()
                .couponType(couponType)
                .build();
    }

    public enum CouponStatus {
        ACTIVE,
        USED,
        EXPIRED
    }
}