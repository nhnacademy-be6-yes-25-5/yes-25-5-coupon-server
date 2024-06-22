package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.application.adapter.UserAdapter;
import com.nhnacademy.couponapi.common.exception.UserCouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    public void issueBirthdayCoupons() {
        List<UserResponseDTO> users = userAdapter.findAllUsers();  // 생일 쿠폰 발급 대상자 조회
        LocalDate currentDate = LocalDate.now();

        users.stream()
                .filter(user -> {
                    LocalDate birthDate = user.userBirth().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return birthDate.getMonthValue() == currentDate.getMonthValue();
                })
                .forEach(user -> {
                    couponService.issueBirthdayCoupon(user.userId());
                });
    }

    @Override
    public void issueWelcomeCoupon(Long userId) {
        try {
            couponService.issueWelcomeCoupon(userId);
        } catch (Exception e) {
            throw new UserCouponServiceException(
                    ErrorStatus.toErrorStatus("Failed to issue welcome coupon", 500, LocalDateTime.now())
            );
        }
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