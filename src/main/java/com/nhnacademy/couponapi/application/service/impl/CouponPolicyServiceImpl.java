package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyResponseDTO> findAllCouponPolicies() {
        return couponPolicyRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicyResponseDTO findCouponPolicyById(Long id) {

        CouponPolicy couponPolicy = couponPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));

        return toResponseDTO(couponPolicy);
    } // 클라이언트에게 응답하기 위해 CouponPolicyResponseDTO로 변환

    @Override
    @Transactional(readOnly = true)
    public CouponPolicy findCouponPolicyEntityById(Long id) {
        return couponPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicy not found"));
    } // 서비스 레이어나 다른 비즈니스 로직에서 직접 엔티티가 필요할 때 사용

    @Override
    public CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO couponPolicyRequestDTO) {

        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyName(couponPolicyRequestDTO.couponPolicyName())
                .couponPolicyDiscountValue(couponPolicyRequestDTO.couponPolicyDiscountValue())
                .couponPolicyRate(couponPolicyRequestDTO.couponPolicyRate())
                .couponPolicyMinOrderAmount(couponPolicyRequestDTO.couponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(couponPolicyRequestDTO.couponPolicyMaxAmount())
                .couponPolicyDiscountType(couponPolicyRequestDTO.couponPolicyDiscountType())
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
                .couponPolicyName(couponPolicyRequestDTO.couponPolicyName())
                .couponPolicyDiscountValue(couponPolicyRequestDTO.couponPolicyDiscountValue())
                .couponPolicyRate(couponPolicyRequestDTO.couponPolicyRate())
                .couponPolicyMinOrderAmount(couponPolicyRequestDTO.couponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(couponPolicyRequestDTO.couponPolicyMaxAmount())
                .couponPolicyDiscountType(couponPolicyRequestDTO.couponPolicyDiscountType())
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
