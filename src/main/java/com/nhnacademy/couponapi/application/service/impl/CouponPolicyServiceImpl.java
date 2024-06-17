package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;

    @Override
    public List<CouponPolicyResponseDTO> getAllCouponPolicies() {
        return couponPolicyRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CouponPolicyResponseDTO getCouponPolicyById(Long id) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));
        return toResponseDTO(couponPolicy);
    }

    @Override
    public CouponPolicy getCouponPolicyEntityById(Long id) {
        return couponPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));
    }

    @Override
    public CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyName(couponPolicyRequestDTO.getCouponPolicyName())
                .couponPolicyDiscountValue(couponPolicyRequestDTO.getCouponPolicyDiscountValue())
                .couponPolicyCreatedAt(couponPolicyRequestDTO.getCouponPolicyCreatedAt())
                .couponPolicyUpdatedAt(couponPolicyRequestDTO.getCouponPolicyUpdatedAt())
                .couponPolicyRate(couponPolicyRequestDTO.getCouponPolicyRate())
                .couponPolicyMinOrderAmount(couponPolicyRequestDTO.getCouponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(couponPolicyRequestDTO.getCouponPolicyMaxAmount())
                .couponPolicyDiscountType(couponPolicyRequestDTO.isCouponPolicyDiscountType())
                .build();

        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);
        return toResponseDTO(savedCouponPolicy);
    }

    @Override
    public CouponPolicyResponseDTO updateCouponPolicy(Long id, CouponPolicyRequestDTO couponPolicyRequestDTO) {
        CouponPolicy existingCouponPolicy = couponPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));

        CouponPolicy updatedCouponPolicy = CouponPolicy.builder()
                .couponPolicyId(existingCouponPolicy.getCouponPolicyId())
                .couponPolicyName(couponPolicyRequestDTO.getCouponPolicyName())
                .couponPolicyDiscountValue(couponPolicyRequestDTO.getCouponPolicyDiscountValue())
                .couponPolicyCreatedAt(couponPolicyRequestDTO.getCouponPolicyCreatedAt())
                .couponPolicyUpdatedAt(couponPolicyRequestDTO.getCouponPolicyUpdatedAt())
                .couponPolicyRate(couponPolicyRequestDTO.getCouponPolicyRate())
                .couponPolicyMinOrderAmount(couponPolicyRequestDTO.getCouponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(couponPolicyRequestDTO.getCouponPolicyMaxAmount())
                .couponPolicyDiscountType(couponPolicyRequestDTO.isCouponPolicyDiscountType())
                .build();

        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(updatedCouponPolicy);
        return toResponseDTO(savedCouponPolicy);
    }

    @Override
    public void deleteCouponPolicy(Long id) {
        couponPolicyRepository.deleteById(id);
    }

    private CouponPolicyResponseDTO toResponseDTO(CouponPolicy couponPolicy) {
        return CouponPolicyResponseDTO.builder()
                .couponPolicyId(couponPolicy.getCouponPolicyId())
                .couponPolicyName(couponPolicy.getCouponPolicyName())
                .couponPolicyDiscountValue(couponPolicy.getCouponPolicyDiscountValue())
                .couponPolicyCreatedAt(couponPolicy.getCouponPolicyCreatedAt())
                .couponPolicyUpdatedAt(couponPolicy.getCouponPolicyUpdatedAt())
                .couponPolicyRate(couponPolicy.getCouponPolicyRate())
                .couponPolicyMinOrderAmount(couponPolicy.getCouponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(couponPolicy.getCouponPolicyMaxAmount())
                .couponPolicyDiscountType(couponPolicy.isCouponPolicyDiscountType())
                .build();
    }
}
