package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;

import java.util.List;

public interface CouponPolicyService {
    List<CouponPolicyResponseDTO> getAllCouponPolicies();
    CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO requestDTO);
}
