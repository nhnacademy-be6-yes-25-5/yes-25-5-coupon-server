package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyBookRepository extends JpaRepository<CouponPolicyBook, Long> {
}