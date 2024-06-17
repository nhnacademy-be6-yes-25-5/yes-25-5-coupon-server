package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.application.adapter.UserAdapter;
import com.nhnacademy.couponapi.persistance.domain.Coupon;
import com.nhnacademy.couponapi.persistance.domain.UserCoupon;
import com.nhnacademy.couponapi.persistance.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserCouponServiceImpl implements UserCouponService {

    private final UserCouponRepository userCouponRepository;

    private final CouponService couponService;

    private final UserAdapter userAdapter;

    @Override
    public List<UserCouponResponseDTO> getAllUserCoupons() {
        return userCouponRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserCouponResponseDTO getUserCouponById(Long id) {
        UserCoupon userCoupon = userCouponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserCoupon not found"));
        return toResponseDTO(userCoupon);
    }

    @Override
    public UserCouponResponseDTO createUserCoupon(UserCouponRequestDTO userCouponRequestDTO) {
        Coupon coupon = couponService.getCouponEntityById(userCouponRequestDTO.getCouponId());
        userAdapter.getUserById(userCouponRequestDTO.getUserId()); // Verify user exists
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(userCouponRequestDTO.getUserId())
                .coupon(coupon)
                .userCouponUsedAt(userCouponRequestDTO.getUserCouponUsedAt())
                .build();
        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);
        return toResponseDTO(savedUserCoupon);
    }

    @Override
    public UserCouponResponseDTO updateUserCoupon(Long id, UserCouponRequestDTO userCouponRequestDTO) {
        UserCoupon userCoupon = userCouponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserCoupon not found"));
        Coupon coupon = couponService.getCouponEntityById(userCouponRequestDTO.getCouponId());
        userAdapter.getUserById(userCouponRequestDTO.getUserId()); // Verify user exists
        userCoupon.setCoupon(coupon);
        userCoupon.setUserId(userCouponRequestDTO.getUserId());
        userCoupon.setUserCouponUsedAt(userCouponRequestDTO.getUserCouponUsedAt());
        UserCoupon updatedUserCoupon = userCouponRepository.save(userCoupon);
        return toResponseDTO(updatedUserCoupon);
    }

    @Override
    public void deleteUserCoupon(Long id) {
        userCouponRepository.deleteById(id);
    }

    private UserCouponResponseDTO toResponseDTO(UserCoupon userCoupon) {
        return UserCouponResponseDTO.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCoupon().getCouponId())
                .userCouponUsedAt(userCoupon.getUserCouponUsedAt())
                .build();
    }
}
