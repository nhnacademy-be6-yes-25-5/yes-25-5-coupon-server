package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.CategoryAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyCategoryServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyCategoryServiceImpl implements CouponPolicyCategoryService {

    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
    private final CouponPolicyService couponPolicyService;
    private final CategoryAdapter categoryAdapter;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyCategoryResponseDTO> findAllCouponPolicyCategories() {
        return couponPolicyCategoryRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicyCategoryResponseDTO findCouponPolicyCategoryById(Long id) {

        CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
                .orElseThrow(() -> new CouponPolicyCategoryServiceException(
                        ErrorStatus.toErrorStatus("Coupon policy category by id", 404, LocalDateTime.now())
                ));

        return toResponseDTO(couponPolicyCategory);
    }

    @Override
    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO) {

        try {
            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
            categoryAdapter.getCategoryById(requestDTO.categoryId());
            CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
                    .couponPolicy(couponPolicy)
                    .categoryId(requestDTO.categoryId())
                    .build();
            CouponPolicyCategory savedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

            return toResponseDTO(savedCouponPolicyCategory);
        } catch (Exception e) {
            throw new CouponPolicyCategoryServiceException(
                    ErrorStatus.toErrorStatus("Coupon policy category creation failed", 500, LocalDateTime.now())
            );
        }

    }

    @Override
    public CouponPolicyCategoryResponseDTO updateCouponPolicyCategory(Long id, CouponPolicyCategoryRequestDTO requestDTO) {

        try {
            CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("CouponPolicyCategory not found"));
            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
            categoryAdapter.getCategoryById(requestDTO.categoryId());
            couponPolicyCategory.setCouponPolicy(couponPolicy);
            couponPolicyCategory.setCategoryId(requestDTO.categoryId());
            CouponPolicyCategory updatedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

            return toResponseDTO(updatedCouponPolicyCategory);
        } catch (Exception e) {
            throw new CouponPolicyCategoryServiceException(
                    ErrorStatus.toErrorStatus("Coupon policy category update failed", 500, LocalDateTime.now())
            );
        }

    }

    @Override
    public void deleteCouponPolicyCategory(Long id) {
        try {
            couponPolicyCategoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new CouponPolicyCategoryServiceException(
                    ErrorStatus.toErrorStatus("Coupon policy category delete failed", 500, LocalDateTime.now())
            );
        }
    }

    private CouponPolicyCategoryResponseDTO toResponseDTO(CouponPolicyCategory couponPolicyCategory) {
        return CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(couponPolicyCategory.getCouponPolicyCategoryId())
                .couponPolicyId(couponPolicyCategory.getCouponPolicy().getCouponPolicyId())
                .categoryId(couponPolicyCategory.getCategoryId())
                .build();
    }

}
