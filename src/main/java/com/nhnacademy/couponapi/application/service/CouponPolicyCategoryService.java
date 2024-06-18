package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;

import java.util.List;

public interface CouponPolicyCategoryService {

    List<CouponPolicyCategoryResponseDTO> findAllCouponPolicyCategories();
    CouponPolicyCategoryResponseDTO findCouponPolicyCategoryById(Long id);
    CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO);
    CouponPolicyCategoryResponseDTO updateCouponPolicyCategory(Long id, CouponPolicyCategoryRequestDTO requestDTO);
    void deleteCouponPolicyCategory(Long id);

}
