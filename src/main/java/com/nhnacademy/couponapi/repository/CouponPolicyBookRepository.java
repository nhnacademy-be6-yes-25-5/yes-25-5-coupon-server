package com.nhnacademy.couponapi.repository;

import com.nhnacademy.couponapi.model.CouponPolicyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponPolicyBookRepository extends JpaRepository<CouponPolicyBook, Long> {
}
