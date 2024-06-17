package com.nhnacademy.couponapi.persistance.repository;

import com.nhnacademy.couponapi.persistance.domain.CouponPolicyTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyTagRepository extends JpaRepository<CouponPolicyTag, Long> {
}
