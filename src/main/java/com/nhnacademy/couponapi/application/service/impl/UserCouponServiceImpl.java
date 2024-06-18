package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.application.adapter.UserAdapter;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCouponServiceImpl implements UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponService couponService;
    private final UserAdapter userAdapter;

    @Override
    @Transactional(readOnly = true)
    public List<UserCouponResponseDTO> getAllUserCoupons() {
        return userCouponRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserCouponResponseDTO getUserCouponById(Long id) {

        UserCoupon userCoupon = userCouponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserCoupon not found"));

        return toResponseDTO(userCoupon);
    }

    @Override
    public UserCouponResponseDTO createUserCoupon(UserCouponRequestDTO userCouponRequestDTO) {

        Coupon coupon = couponService.getCouponEntityById(userCouponRequestDTO.couponId());
        userAdapter.getUserById(userCouponRequestDTO.userId());

        UserCoupon userCoupon = UserCoupon.builder()
                .userId(userCouponRequestDTO.userId())
                .coupon(coupon)
                .userCouponUsedAt(userCouponRequestDTO.userCouponUsedAt())
                .build();

        UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);

        return toResponseDTO(savedUserCoupon);
    }

    @Override
    public UserCouponResponseDTO updateUserCoupon(Long id, UserCouponRequestDTO userCouponRequestDTO) {

        UserCoupon userCoupon = userCouponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserCoupon not found"));

        Coupon coupon = couponService.getCouponEntityById(userCouponRequestDTO.couponId());

        userAdapter.getUserById(userCouponRequestDTO.userId());
        userCoupon.setCoupon(coupon);
        userCoupon.setUserId(userCouponRequestDTO.userId());
        userCoupon.setUserCouponUsedAt(userCouponRequestDTO.userCouponUsedAt());
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
