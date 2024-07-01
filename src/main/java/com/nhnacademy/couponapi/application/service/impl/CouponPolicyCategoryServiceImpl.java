package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyCategoryServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
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
public class CouponPolicyCategoryServiceImpl implements CouponPolicyCategoryService {

    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyCategoryResponseDTO> findAllCouponPolicyCategories() {
        return couponPolicyCategoryRepository.findAll().stream()
                .map(CouponPolicyCategoryResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO) {
        try {
            // 쿠폰 정책 생성
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(requestDTO.getCouponPolicyName())
                    .couponPolicyDiscountValue(requestDTO.getCouponPolicyDiscountValue())
                    .couponPolicyRate(requestDTO.getCouponPolicyRate())
                    .couponPolicyMinOrderAmount(requestDTO.getCouponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(requestDTO.getCouponPolicyMaxAmount())
                    .couponPolicyDiscountType(requestDTO.isCouponPolicyDiscountType())
                    .build();
            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            // 카테고리 쿠폰 정책 생성
            CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
                    .couponPolicy(savedCouponPolicy)
                    .categoryId(requestDTO.getCategoryId())
                    .categoryName(requestDTO.getCategoryName())
                    .build();
            CouponPolicyCategory savedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

            // 쿠폰 생성
            couponCreationUtil.createCoupon(savedCouponPolicy);

            return CouponPolicyCategoryResponseDTO.builder()
                    .couponPolicyCategoryId(savedCouponPolicyCategory.getCouponPolicyCategoryId())
                    .categoryId(savedCouponPolicyCategory.getCategoryId())
                    .categoryName(savedCouponPolicyCategory.getCategoryName())
                    .couponPolicy(CouponPolicyResponseDTO.fromEntity(savedCouponPolicyCategory.getCouponPolicy()))
                    .build();

        } catch (Exception e) {
            String errorMessage = "An unexpected error occurred while creating CouponPolicyCategory.";
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new CouponPolicyCategoryServiceException(
                    ErrorStatus.toErrorStatus("Category coupon policy does not saved", 500, LocalDateTime.now())
            );
        }
    }
}