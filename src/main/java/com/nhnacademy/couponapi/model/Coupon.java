package com.nhnacademy.couponapi.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(nullable = false)
    private String couponName;

    @Column(nullable = true)
    private String couponCode;

    @Column(nullable = false)
    private Date couponExpiredAt;

    @Column(nullable = false)
    private Date couponCreatedAt;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    // Getters and Setters

}
