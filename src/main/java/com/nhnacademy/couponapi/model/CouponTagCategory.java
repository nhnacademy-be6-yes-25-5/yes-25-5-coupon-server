//package com.nhnacademy.couponapi.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "coupon_tag_category")
//public class CouponTagCategory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long couponPolicyCategoryId;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_policy_id", nullable = false)
//    private CouponPolicy couponPolicy;
//
//    @Column(nullable = false)
//    private Long categoryId;
//
//    // Getters and Setters
//}
