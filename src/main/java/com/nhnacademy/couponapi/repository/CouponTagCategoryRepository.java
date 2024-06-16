package com.nhnacademy.couponapi.repository;

import com.nhnacademy.couponapi.model.CouponTagCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponTagCategoryRepository extends JpaRepository<CouponTagCategory, Long> {
}
