package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;

import java.util.List;

public interface UserCouponService {
    void deleteUserCoupon(Long id);
    List<CouponUserListResponseDTO> findUserCoupons(Long userId);
    void issueBirthdayCoupons();  // 생일 쿠폰 발급 메소드
    void issueWelcomeCoupon(Long userId);  // 웰컴 쿠폰 발급 메소드
}