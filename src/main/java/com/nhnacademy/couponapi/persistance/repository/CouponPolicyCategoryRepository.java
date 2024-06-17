package com.nhnacademy.couponapi.persistance.repository;

import com.nhnacademy.couponapi.persistance.domain.CouponPolicyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyCategoryRepository extends JpaRepository<CouponPolicyCategory, Long> {
}
