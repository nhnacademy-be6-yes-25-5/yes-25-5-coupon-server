package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.UserAdapter;
import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.common.exception.FeignClientException;
import com.nhnacademy.couponapi.common.exception.UserCouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserCouponServiceImpl.class);
    private static final int MAX_RETRY_COUNT = 3;

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
        List<UserResponseDTO> users;
        try {
            users = userAdapter.findAllUsers();  // 생일 쿠폰 발급 대상자 조회
        } catch (Exception e) {
            throw new FeignClientException(
                    ErrorStatus.toErrorStatus("Failed to retrieve users from user service", 500, LocalDateTime.now())
            );
        }

        LocalDate currentDate = LocalDate.now();

        users.stream()
                .filter(user -> {
                    LocalDate birthDate = user.userBirth().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return birthDate.getMonthValue() == currentDate.getMonthValue();
                })
                .forEach(user -> {
                    boolean success = issueCouponWithRetry(() -> couponService.issueBirthdayCoupon(user.userId()));
                    if (!success) {
                        logger.error("Failed to issue birthday coupon for user {}", user.userId());
                    }
                });
    }

//    @Override // 생일쿠폰 테스트용
//    public void issueBirthdayCouponForUser(Long userId) {
//        UserResponseDTO user;
//        try {
//            user = userAdapter.getUserById(userId);  // 특정 사용자 조회
//        } catch (Exception e) {
//            throw new FeignClientException(
//                    ErrorStatus.toErrorStatus("Failed to retrieve user from user service", 500, LocalDateTime.now())
//            );
//        }
//
//        LocalDate currentDate = LocalDate.now();
//        LocalDate birthDate = user.userBirth().toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
//
//        if (birthDate.getMonthValue() == currentDate.getMonthValue()) {
//            boolean success = issueCouponWithRetry(() -> couponService.issueBirthdayCoupon(user.userId()));
//            if (!success) {
//                logger.error("Failed to issue birthday coupon for user {}", user.userId());
//            } else {
//                logger.info("Issued birthday coupon for user {}", user.userId());
//            }
//        } else {
//            logger.info("User {} does not have a birthday this month", user.userId());
//        }
//    }

    @Override
    public void issueWelcomeCoupon(Long userId) {
        boolean success = issueCouponWithRetry(() -> couponService.issueWelcomeCoupon(userId));
        if (!success) {
            logger.error("Failed to issue welcome coupon for user {}", userId);
        }
    }

    private boolean issueCouponWithRetry(Runnable issueCouponTask) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                issueCouponTask.run();
                return true;
            } catch (Exception e) {
                retryCount++;
                logger.warn("Coupon issuance failed, retrying {}/{}", retryCount, MAX_RETRY_COUNT, e);
                if (retryCount == MAX_RETRY_COUNT) {
                    throw new FeignClientException(
                            ErrorStatus.toErrorStatus("Failed to issue coupon after retries", 500, LocalDateTime.now())
                    );
                }
            }
        }
        return false;
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