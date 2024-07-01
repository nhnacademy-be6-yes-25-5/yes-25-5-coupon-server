package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyResponseDTO> findAllCouponPolicies() {
        return couponPolicyRepository.findAll().stream()
                .map(CouponPolicyResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicyResponseDTO findCouponPolicyById(Long id) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(id)
                .orElseThrow(() -> new CouponPolicyServiceException(
                        ErrorStatus.toErrorStatus("Coupon policy not found by id", 404, LocalDateTime.now())
                ));
        return CouponPolicyResponseDTO.fromEntity(couponPolicy);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicy findCouponPolicyEntityById(Long id) {
        return couponPolicyRepository.findById(id)
                .orElseThrow(() -> new CouponPolicyServiceException(
                        ErrorStatus.toErrorStatus("Coupon policy not found by id", 404, LocalDateTime.now())
                ));
    }

    @Override
    public CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO couponPolicyRequestDTO) {
        try {
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(couponPolicyRequestDTO.couponPolicyName())
                    .couponPolicyDiscountValue(couponPolicyRequestDTO.couponPolicyDiscountValue())
                    .couponPolicyRate(couponPolicyRequestDTO.couponPolicyRate())
                    .couponPolicyMinOrderAmount(couponPolicyRequestDTO.couponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(couponPolicyRequestDTO.couponPolicyMaxAmount())
                    .couponPolicyDiscountType(couponPolicyRequestDTO.couponPolicyDiscountType())
                    .build();

            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            couponCreationUtil.createCoupon(savedCouponPolicy);

            return CouponPolicyResponseDTO.fromEntity(savedCouponPolicy);
        } catch (Exception e) {
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus("Coupon Policy creation failed", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public CouponPolicyResponseDTO updateCouponPolicy(Long id, CouponPolicyRequestDTO couponPolicyRequestDTO) {
        try {
            CouponPolicy existingCouponPolicy = couponPolicyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));

            CouponPolicy updatedCouponPolicy = CouponPolicy.builder()
                    .couponPolicyId(existingCouponPolicy.getCouponPolicyId())
                    .couponPolicyName(couponPolicyRequestDTO.couponPolicyName())
                    .couponPolicyDiscountValue(couponPolicyRequestDTO.couponPolicyDiscountValue())
                    .couponPolicyRate(couponPolicyRequestDTO.couponPolicyRate())
                    .couponPolicyMinOrderAmount(couponPolicyRequestDTO.couponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(couponPolicyRequestDTO.couponPolicyMaxAmount())
                    .couponPolicyDiscountType(couponPolicyRequestDTO.couponPolicyDiscountType())
                    .build();

            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(updatedCouponPolicy);

            return CouponPolicyResponseDTO.fromEntity(savedCouponPolicy);
        } catch (Exception e) {
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus("Coupon Policy update failed", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public void deleteCouponPolicy(Long id) {
        try {
            couponPolicyRepository.deleteById(id);
        } catch (Exception e) {
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus("Coupon Policy delete failed", 500, LocalDateTime.now())
            );
        }
    }
}