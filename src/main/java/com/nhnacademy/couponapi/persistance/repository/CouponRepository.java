package com.nhnacademy.couponapi.persistance.repository;

import com.nhnacademy.couponapi.persistance.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
