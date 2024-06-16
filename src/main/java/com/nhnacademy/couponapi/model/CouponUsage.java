package com.nhnacademy.couponapi.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "coupon_usage")
public class CouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;

    @Column(nullable = true)
    private Date userCouponUsedAt;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private Long userId;

    // Getters and Setters
}
