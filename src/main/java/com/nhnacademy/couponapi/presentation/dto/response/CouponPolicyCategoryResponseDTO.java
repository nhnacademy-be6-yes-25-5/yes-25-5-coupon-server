package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import lombok.Builder;

@Builder
public record CouponPolicyCategoryResponseDTO(
        Long couponPolicyCategoryId,
        Long categoryId,
        String categoryName,
        CouponPolicyResponseDTO couponPolicy) {

    public static CouponPolicyCategoryResponseDTO fromEntity(CouponPolicyCategory couponPolicyCategory) {
        return new CouponPolicyCategoryResponseDTO(
                couponPolicyCategory.getCouponPolicyCategoryId(),
                couponPolicyCategory.getCategoryId(),
                couponPolicyCategory.getCategoryName(),
                CouponPolicyResponseDTO.fromEntity(couponPolicyCategory.getCouponPolicy())
        );
    }

}