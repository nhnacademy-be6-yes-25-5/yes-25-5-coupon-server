package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;

public interface CouponService {

//    List<CouponUserListResponseDTO> findAllCoupons();
    CouponResponseDTO findCouponById(Long id);
//    CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO);
    CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO);
    void deleteCoupon(Long id);
    Coupon findCouponEntityById(Long id);
    CouponResponseDTO createCoupon(Coupon coupon);

//    CouponResponseDTO issueBirthdayCoupon(Long userId);
//    CouponResponseDTO issueWelcomeCoupon(Long userId);
//    List<CouponUserListResponseDTO> getCouponsByBookId(Long bookId);
//    List<CouponUserListResponseDTO> getCouponsByCategoryIds(List<Long> categoryIds);
//    ReadOrderUserCouponResponse findBestCoupon(Long userId, BigDecimal orderAmount);
}