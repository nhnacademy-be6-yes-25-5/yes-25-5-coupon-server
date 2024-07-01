package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public CouponResponseDTO createCoupon(Coupon coupon) {
        try {
            Coupon savedCoupon = couponRepository.save(coupon);
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
                CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId());
                couponBuilder.couponPolicy(couponPolicy);
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

    private String createCouponCode() {
        return UUID.randomUUID().toString();
    }
}