package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;

import java.util.List;

public interface CouponPolicyService {

    List<CouponPolicyResponseDTO> getAllCouponPolicies();
    CouponPolicyResponseDTO getCouponPolicyById(Long id);
    CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO requestDTO);
    CouponPolicyResponseDTO updateCouponPolicy(Long id, CouponPolicyRequestDTO requestDTO);
    void deleteCouponPolicy(Long id);
    CouponPolicy getCouponPolicyEntityById(Long id);

}
