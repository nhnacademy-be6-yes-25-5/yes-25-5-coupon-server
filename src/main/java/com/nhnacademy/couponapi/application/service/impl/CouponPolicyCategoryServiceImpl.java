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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link CouponPolicyCategoryService}의 구현 클래스입니다.
 * 이 클래스는 카테고리별 쿠폰 정책의 생성 및 조회 기능을 제공합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyCategoryServiceImpl implements CouponPolicyCategoryService {

    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    /**
     * 모든 카테고리 쿠폰 정책을 조회합니다.
     *
     * @return 모든 카테고리 쿠폰 정책 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyCategoryResponseDTO> getAllCouponPolicyCategories() {
        return couponPolicyCategoryRepository.findAll().stream()
                .map(CouponPolicyCategoryResponseDTO::fromEntity)
                .toList();
    }

    /**
     * 새로운 카테고리 쿠폰 정책을 생성합니다.
     *
     * @param requestDTO 카테고리 쿠폰 정책 생성에 필요한 정보가 담긴 DTO
     * @return 생성된 카테고리 쿠폰 정책 정보
     * @throws CouponPolicyCategoryServiceException 카테고리 쿠폰 정책 생성 중 예외 발생 시
     */
    @Override
    @Transactional
    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO) {
        try {
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(requestDTO.couponPolicyName())
                    .couponPolicyDiscountValue(requestDTO.couponPolicyDiscountValue())
                    .couponPolicyRate(requestDTO.couponPolicyRate())
                    .couponPolicyMinOrderAmount(requestDTO.couponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(requestDTO.couponPolicyMaxAmount())
                    .couponPolicyDiscountType(requestDTO.couponPolicyDiscountType())
                    .build();
            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
                    .couponPolicy(savedCouponPolicy)
                    .categoryId(requestDTO.categoryId())
                    .categoryName(requestDTO.categoryName())
                    .build();
            CouponPolicyCategory savedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);

            couponCreationUtil.createCoupon(savedCouponPolicy);

            return CouponPolicyCategoryResponseDTO.builder()
                    .couponPolicyCategoryId(savedCouponPolicyCategory.getCouponPolicyCategoryId())
                    .categoryId(savedCouponPolicyCategory.getCategoryId())
                    .categoryName(savedCouponPolicyCategory.getCategoryName())
                    .couponPolicy(CouponPolicyResponseDTO.fromEntity(savedCouponPolicyCategory.getCouponPolicy()))
                    .build();

        } catch (Exception e) {
            String errorMessage = "카테고리 쿠폰 정책 생성 중 예상치 못한 오류가 발생했습니다.";
            log.error(errorMessage, e);
            throw new CouponPolicyCategoryServiceException(
                    ErrorStatus.toErrorStatus("카테고리 쿠폰 정책 저장 실패", 500, LocalDateTime.now())
            );
        }
    }
}