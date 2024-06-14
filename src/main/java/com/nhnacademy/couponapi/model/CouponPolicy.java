//package com.nhnacademy.couponapi.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "coupon_policy")
//public class CouponPolicy {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long couponPolicyId;
//
//    @Column(nullable = false)
//    private String couponPolicyName;
//
//    @Column(nullable = true)
//    private Long couponPolicyDiscountValue;
//
//    @Column(nullable = false)
//    private Date couponPolicyCreatedAt;
//
//    @Column(nullable = true)
//    private Date couponPolicyUpdatedAt;
//
//    @Column(nullable = true)
//    private Integer couponPolicyRate;
//
//    @Column(nullable = false)
//    private Double couponPolicyMinOrderAmount;
//
//    @Column(nullable = true)
//    private Double couponPolicyMaxAmount;
//
//    @Column(nullable = false)
//    private Boolean couponPolicyDiscountType;
//
//    @PrePersist
//    protected void onCreate() {
//        this.couponPolicyCreatedAt = new Date();
//    }
//}
