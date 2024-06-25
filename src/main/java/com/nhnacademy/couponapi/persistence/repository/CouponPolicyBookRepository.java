package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponPolicyBookRepository extends JpaRepository<CouponPolicyBook, Long> {
    List<CouponPolicyBook> findByBookId(Long bookId);
}