package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.CategoryAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponPolicyCategoryServiceImpl implements CouponPolicyCategoryService {

    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
    private final CouponPolicyService couponPolicyService;
    private final CategoryAdapter categoryAdapter;

    @Override
    public List<CouponPolicyCategoryResponseDTO> getAllCouponPolicyCategories() {
        return couponPolicyCategoryRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CouponPolicyCategoryResponseDTO getCouponPolicyCategoryById(Long id) {

        CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyCategory not found"));

        return toResponseDTO(couponPolicyCategory);
    }

    @Override
    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO) {

        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.couponPolicyId());
        categoryAdapter.getCategoryById(requestDTO.categoryId());
        CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
                .couponPolicy(couponPolicy)
                .categoryId(requestDTO.categoryId())
                .build();
        CouponPolicyCategory savedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

        return toResponseDTO(savedCouponPolicyCategory);
    }

    @Override
    public CouponPolicyCategoryResponseDTO updateCouponPolicyCategory(Long id, CouponPolicyCategoryRequestDTO requestDTO) {

        CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyCategory not found"));
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.couponPolicyId());
        categoryAdapter.getCategoryById(requestDTO.categoryId());
        couponPolicyCategory.setCouponPolicy(couponPolicy);
        couponPolicyCategory.setCategoryId(requestDTO.categoryId());
        CouponPolicyCategory updatedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

        return toResponseDTO(updatedCouponPolicyCategory);
    }

    @Override
    public void deleteCouponPolicyCategory(Long id) {
        couponPolicyCategoryRepository.deleteById(id);
    }

    private CouponPolicyCategoryResponseDTO toResponseDTO(CouponPolicyCategory couponPolicyCategory) {
        return CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(couponPolicyCategory.getCouponPolicyCategoryId())
                .couponPolicyId(couponPolicyCategory.getCouponPolicy().getCouponPolicyId())
                .categoryId(couponPolicyCategory.getCategoryId())
                .build();
    }

}
