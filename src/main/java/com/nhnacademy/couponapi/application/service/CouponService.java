package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;

import java.util.List;

public interface CouponService {

    CouponResponseDTO createCoupon(Coupon coupon);
    List<Coupon> getCouponsByBookIdAndCategoryIds(Long bookId, List<Long> categoryIds);
}