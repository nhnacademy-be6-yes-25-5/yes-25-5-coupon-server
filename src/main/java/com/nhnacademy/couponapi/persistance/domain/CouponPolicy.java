package com.nhnacademy.couponapi.persistance.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class CouponPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;
    private int discountValue;
    private Date createdAt;
    private Date updatedAt;
    private int minOrderAmount;
    private int maxDiscountAmount;
    private boolean discountType;

    @OneToMany(mappedBy = "couponPolicy")
    private List<Coupon> coupons;

    // getters and setters
}
