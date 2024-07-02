package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByCouponPolicyIn(List<CouponPolicy> couponPolicies);
}