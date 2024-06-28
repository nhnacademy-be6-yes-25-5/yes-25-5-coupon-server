package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.ReadOrderUserCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyBookRepository couponPolicyBookRepository;
    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
    private final CouponPolicyService couponPolicyService;
    private final UserCouponRepository userCouponRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> findAllCoupons() {
        return couponRepository.findAll().stream()
                .map(coupon -> CouponUserListResponseDTO.fromEntity(coupon.toUserCoupon()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponseDTO findCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("Coupon not found by id", 404, LocalDateTime.now())
                ));
        return CouponResponseDTO.fromEntity(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findCouponEntityById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("Coupon not found by id", 404, LocalDateTime.now())
                ));
    }

    @Override
    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {
        try {
            Coupon.CouponBuilder couponBuilder = Coupon.builder()
                    .couponName(couponRequestDTO.couponName())
                    .couponCode(couponRequestDTO.couponCode())
                    .couponExpiredAt(couponRequestDTO.couponExpiredAt());
            //쿠폰 발급 구현할떄 만료일자 지정하기 ex) 생성일자로부터 2달

            if (couponRequestDTO.couponPolicyId() != null) {
                couponBuilder.couponPolicy(couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
            }

            Coupon savedCoupon = couponRepository.save(couponBuilder.build());
            return CouponResponseDTO.fromEntity(savedCoupon);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be created", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO) {
        try {
            Coupon existingCoupon = couponRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            Coupon.CouponBuilder couponBuilder = Coupon.builder()
                    .couponId(existingCoupon.getCouponId())
                    .couponName(couponRequestDTO.couponName())
                    .couponCode(couponRequestDTO.couponCode())
                    .couponExpiredAt(couponRequestDTO.couponExpiredAt());

            if (couponRequestDTO.couponPolicyId() != null) {
                couponBuilder.couponPolicy(couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
            }

            Coupon updatedCoupon = couponRepository.save(couponBuilder.build());
            return CouponResponseDTO.fromEntity(updatedCoupon);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be updated", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public void deleteCoupon(Long id) {
        try {
            couponRepository.deleteById(id);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be deleted", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    @Transactional
    public CouponResponseDTO issueBirthdayCoupon(Long userId) {
        Date now = new Date();
        Date validTo = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000); // 30일 유효

        Coupon coupon = Coupon.builder()
                .couponName("생일 쿠폰")
                .couponCode(createCouponCode())
                .validFrom(now)
                .validTo(validTo)
                .couponCreatedAt(now)
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponResponseDTO.fromEntity(savedCoupon);
    }

    @Override
    @Transactional
    public CouponResponseDTO issueWelcomeCoupon(Long userId) {
        Date now = new Date();
        Date validTo = new Date(now.getTime() + 60L * 24 * 60 * 60 * 1000); // 60일 유효

        Coupon coupon = Coupon.builder()
                .couponName("웰컴 쿠폰")
                .couponCode(createCouponCode())
                .validFrom(now)
                .validTo(validTo)
                .couponCreatedAt(now)
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponResponseDTO.fromEntity(savedCoupon);
    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CouponUserListResponseDTO> getCouponsByBookId(Long bookId) {
//        return couponPolicyBookRepository.findByBookId(bookId).stream()
//                .flatMap(policy -> policy.getCouponPolicy().getCoupons().stream())
//                .map(coupon -> CouponUserListResponseDTO.fromEntity(coupon.toUserCoupon()))
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> getCouponsByCategoryIds(List<Long> categoryIds) {
        return couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds).stream()
                .flatMap(policy -> policy.getCouponPolicy().getCoupons().stream())
                .map(coupon -> CouponUserListResponseDTO.fromEntity(coupon.toUserCoupon()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReadOrderUserCouponResponse findBestCoupon(Long userId, BigDecimal orderAmount) {
        List<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId).stream()
                .filter(userCoupon -> userCoupon.getCouponStatus() == UserCoupon.CouponStatus.ACTIVE)
                .filter(userCoupon -> userCoupon.getCoupon().getCouponPolicy().getCouponPolicyMinOrderAmount().compareTo(orderAmount) <= 0)
                .toList();

        UserCoupon bestCoupon = userCoupons.stream()
                .max(Comparator.comparing((UserCoupon userCoupon) -> calculateDiscount(userCoupon.getCoupon(), orderAmount))
                        .thenComparing(userCoupon -> userCoupon.getCoupon().getCouponExpiredAt(), Comparator.reverseOrder()))
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("사용할수있는 쿠폰이 없습니다.", 404, LocalDateTime.now())
                ));

        return new ReadOrderUserCouponResponse(bestCoupon.getCoupon().getCouponId(), calculateDiscount(bestCoupon.getCoupon(), orderAmount));
    }

    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount) {
        CouponPolicy policy = coupon.getCouponPolicy();
        if (policy.isCouponPolicyDiscountType()) { // 할인율
            BigDecimal discount = orderAmount.multiply(policy.getCouponPolicyRate());
            return discount.compareTo(policy.getCouponPolicyMaxAmount()) > 0 ? policy.getCouponPolicyMaxAmount() : discount;
        } else { // 할인금액
            return policy.getCouponPolicyDiscountValue();
        }
    }

    private String createCouponCode() {
        return UUID.randomUUID().toString();
    }

}