package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
}
