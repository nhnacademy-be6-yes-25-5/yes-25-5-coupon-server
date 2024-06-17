package com.nhnacademy.couponapi.persistance.repository;

import com.nhnacademy.couponapi.persistance.domain.CouponPolicyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyBookRepository extends JpaRepository<CouponPolicyBook, Long> {
}
