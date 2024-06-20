package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;

import java.util.List;

public interface UserCouponService {

//    List<UserCouponResponseDTO> findAllUserCoupons();
//    UserCouponResponseDTO findUserCouponById(Long id);
//    UserCouponResponseDTO createUserCoupon(UserCouponRequestDTO userCouponRequestDTO);
//    UserCouponResponseDTO updateUserCoupon(Long id, UserCouponRequestDTO userCouponRequestDTO);
    void deleteUserCoupon(Long id);
    List<CouponUserListResponseDTO> findUserCoupons(Long userId);
}
