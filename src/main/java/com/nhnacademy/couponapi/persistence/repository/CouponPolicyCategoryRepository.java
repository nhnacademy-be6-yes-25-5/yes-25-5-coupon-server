package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponPolicyCategoryRepository extends JpaRepository<CouponPolicyCategory, Long> {
    List<CouponPolicyCategory> findByCategoryIdIn(List<Long> categoryIds);
}