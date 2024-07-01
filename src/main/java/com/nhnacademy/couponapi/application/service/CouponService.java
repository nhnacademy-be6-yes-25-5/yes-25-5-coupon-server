package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;

public interface CouponService {

    CouponResponseDTO createCoupon(Coupon coupon);
}