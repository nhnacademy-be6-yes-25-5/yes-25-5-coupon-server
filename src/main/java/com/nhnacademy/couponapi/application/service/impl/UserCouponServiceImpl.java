package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.application.adapter.UserAdapter;
import com.nhnacademy.couponapi.common.exception.UserCouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserCouponServiceImpl implements UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponService couponService;
    private final UserAdapter userAdapter;

//    @Override
//    @Transactional(readOnly = true)
//    public List<UserCouponResponseDTO> findAllUserCoupons() {
//        return userCouponRepository.findAll().stream()
//                .map(this::toResponseDTO)
//                .collect(Collectors.toList());
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserCouponResponseDTO findUserCouponById(Long id) {
//        UserCoupon userCoupon = userCouponRepository.findById(id)
//                .orElseThrow(() -> new UserCouponServiceException(
//                        ErrorStatus.toErrorStatus("User coupon not found by id", 404, LocalDateTime.now())
//                ));
//        return toResponseDTO(userCoupon);
//    }

//    @Override
//    public UserCouponResponseDTO createUserCoupon(UserCouponRequestDTO userCouponRequestDTO) {
//        try {
//            Coupon coupon = couponService.findCouponEntityById(userCouponRequestDTO.couponId());
//            userAdapter.getUserById(userCouponRequestDTO.userId());
//
//            UserCoupon userCoupon = UserCoupon.builder()
//                    .userId(userCouponRequestDTO.userId())
//                    .coupon(coupon)
//                    .userCouponUsedAt(userCouponRequestDTO.userCouponUsedAt())
//                    .build();
//
//            UserCoupon savedUserCoupon = userCouponRepository.save(userCoupon);
//
//            return toResponseDTO(savedUserCoupon);
//        } catch (Exception e) {
//            throw new UserCouponServiceException(
//                    ErrorStatus.toErrorStatus("Failed to create user coupon", 500, LocalDateTime.now())
//            );
//        }
//    }
//
//    @Override
//    public UserCouponResponseDTO updateUserCoupon(Long id, UserCouponRequestDTO userCouponRequestDTO) {
//        try {
//            UserCoupon userCoupon = userCouponRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("UserCoupon not found"));
//
//            Coupon coupon = couponService.findCouponEntityById(userCouponRequestDTO.couponId());
//
//            userAdapter.getUserById(userCouponRequestDTO.userId());
//            userCoupon.setCoupon(coupon);
//            userCoupon.setUserId(userCouponRequestDTO.userId());
//            userCoupon.setUserCouponUsedAt(userCouponRequestDTO.userCouponUsedAt());
//            UserCoupon updatedUserCoupon = userCouponRepository.save(userCoupon);
//
//            return toResponseDTO(updatedUserCoupon);
//        } catch (Exception e) {
//            throw new UserCouponServiceException(
//                    ErrorStatus.toErrorStatus("Failed to update user coupon", 500, LocalDateTime.now())
//            );
//        }
//    }

    @Override
    public void deleteUserCoupon(Long id) {
        try {
            userCouponRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserCouponServiceException(
                    ErrorStatus.toErrorStatus("Failed to delete user coupon", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> findUserCoupons(Long userId) {
        return userCouponRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private CouponUserListResponseDTO toResponseDTO(UserCoupon userCoupon) {
        return CouponUserListResponseDTO.builder()
                .userCouponId(userCoupon.getUserCouponId())
                .userId(userCoupon.getUserId())
                .couponId(userCoupon.getCoupon().getCouponId())
                .couponName(userCoupon.getCoupon().getCouponName())
                .couponCode(userCoupon.getCoupon().getCouponCode())
                .couponPolicyDiscountValue(userCoupon.getCoupon().getCouponPolicy() != null ? userCoupon.getCoupon().getCouponPolicy().getCouponPolicyDiscountValue() : null)
                .couponPolicyRate(userCoupon.getCoupon().getCouponPolicy() != null ? userCoupon.getCoupon().getCouponPolicy().getCouponPolicyRate() : null)
                .couponPolicyMinOrderAmount(userCoupon.getCoupon().getCouponPolicy() != null ? userCoupon.getCoupon().getCouponPolicy().getCouponPolicyMinOrderAmount() : null)
                .couponPolicyMaxAmount(userCoupon.getCoupon().getCouponPolicy() != null ? userCoupon.getCoupon().getCouponPolicy().getCouponPolicyMaxAmount() : null)
                .couponCreatedAt(userCoupon.getCoupon().getCouponCreatedAt())
                .couponExpiredAt(userCoupon.getCoupon().getCouponExpiredAt())
                .userCouponUsedAt(userCoupon.getUserCouponUsedAt())
                .build();
    }
}