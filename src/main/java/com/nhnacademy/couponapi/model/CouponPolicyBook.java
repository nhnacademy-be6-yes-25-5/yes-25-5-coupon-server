//package com.nhnacademy.couponapi.model;
//
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "coupon_policy_book")
//public class CouponPolicyBook {
//    @NotNull
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long couponPolicyBookId;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_policy_id", nullable = false)
//    private CouponPolicy couponPolicy;
//
//    @Column(nullable = false)
//    private Long bookId;
//
//    // Getters and Setters
//}
