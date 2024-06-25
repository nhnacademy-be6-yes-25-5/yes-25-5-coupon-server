package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;

import java.util.List;

public interface UserCouponService {
    void deleteUserCoupon(Long id);
    List<CouponUserListResponseDTO> findUserCoupons(Long userId);
    void issueBirthdayCoupons();  // 생일 쿠폰 발급 메소드
//    void issueBirthdayCouponForUser(Long userId); 특정사용자에게 생일쿠폰 발급 -> 테스트용
    void issueWelcomeCoupon(Long userId);  // 웰컴 쿠폰 발급 메소드
}