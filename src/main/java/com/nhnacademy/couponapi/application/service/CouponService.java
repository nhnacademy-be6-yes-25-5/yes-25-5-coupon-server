package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;

import java.util.List;

public interface CouponService {

    List<CouponResponseDTO> getAllCoupons();
    CouponResponseDTO getCouponById(Long id);
    CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO);
    CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO);
    void deleteCoupon(Long id);
    Coupon getCouponEntityById(Long id);

}
