package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> findAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::toUserListResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponseDTO findCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("Coupon not found by id", 404, LocalDateTime.now())
                ));
        return toResponseDTO(coupon);
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

            if (couponRequestDTO.couponPolicyId() != null) {
                couponBuilder.couponPolicy(couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
            }

            Coupon savedCoupon = couponRepository.save(couponBuilder.build());
            return toResponseDTO(savedCoupon);
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
            return toResponseDTO(updatedCoupon);
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
        return toResponseDTO(savedCoupon);
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
        return toResponseDTO(savedCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> getCouponsByBookId(Long bookId) {
        return couponPolicyBookRepository.findByBookId(bookId).stream()
                .flatMap(policy -> policy.getCouponPolicy().getCoupons().stream())
                .map(this::toUserListResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> getCouponsByCategoryIds(List<Long> categoryIds) {
        return couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds).stream()
                .flatMap(policy -> policy.getCouponPolicy().getCoupons().stream())
                .map(this::toUserListResponseDTO)
                .collect(Collectors.toList());
    }

    private String createCouponCode() {
        return UUID.randomUUID().toString();
    }

    private CouponResponseDTO toResponseDTO(Coupon coupon) {
        return CouponResponseDTO.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponCode(coupon.getCouponCode())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .couponCreatedAt(coupon.getCouponCreatedAt())
                .couponPolicyId(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyId() : null)
                .build();
    }

    private CouponUserListResponseDTO toUserListResponseDTO(Coupon coupon) {
        return CouponUserListResponseDTO.builder()
                .userCouponId(1L)
                .userId(4L)
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponCode(coupon.getCouponCode())
                .couponPolicyDiscountValue(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyDiscountValue() : null)
                .couponPolicyRate(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyRate() : null)
                .couponPolicyMinOrderAmount(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyMinOrderAmount() : null)
                .couponPolicyMaxAmount(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyMaxAmount() : null)
                .couponCreatedAt(coupon.getCouponCreatedAt())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .userCouponUsedAt(new Date())
                .build();
    }
}