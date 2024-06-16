//package com.nhnacademy.couponapi.persistance.domain;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//public class CouponUsage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long userId;
//    private Long couponId;
//    private Date usedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
//
//    // getters and setters
//}
