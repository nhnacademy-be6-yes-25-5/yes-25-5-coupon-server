package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyTagRepository extends JpaRepository<CouponPolicyTag, Long> {
}
