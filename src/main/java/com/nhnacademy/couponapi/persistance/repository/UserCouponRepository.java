package com.nhnacademy.couponapi.persistance.repository;

import com.nhnacademy.couponapi.persistance.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
}
