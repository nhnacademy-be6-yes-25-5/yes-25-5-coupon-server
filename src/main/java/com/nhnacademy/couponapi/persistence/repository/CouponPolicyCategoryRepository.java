package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyCategoryRepository extends JpaRepository<CouponPolicyCategory, Long> {
}
