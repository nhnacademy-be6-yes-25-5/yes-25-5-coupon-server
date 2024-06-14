//package com.nhnacademy.couponapi.model;
//
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "coupon_policy_tag")
//public class CouponPolicyTag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long couponPolicyTagId;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_policy_id", nullable = false)
//    private CouponPolicy couponPolicy;
//
//    @Column(nullable = false)
//    private Long tagId;
//
//    // Getters and Setters
//}
