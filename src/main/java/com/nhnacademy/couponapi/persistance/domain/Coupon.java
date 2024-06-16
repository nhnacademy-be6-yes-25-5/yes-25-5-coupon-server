//package com.nhnacademy.couponapi.persistance.domain;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//public class Coupon {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String couponCode;
//    private Date expiryDate;
//    private Long userId;
//    private boolean isUsed;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_policy_id")
//    private CouponPolicy couponPolicy;
//
//    // getters and setters
//}
