package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
}
