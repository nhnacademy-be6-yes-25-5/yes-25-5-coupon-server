package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import lombok.Builder;

@Builder
public record CouponPolicyCategoryResponseDTO(
        Long couponPolicyCategoryId,
        Long couponPolicyId,
        Long categoryId) {

    public static CouponPolicyCategoryResponseDTO fromEntity(CouponPolicyCategory couponPolicyCategory) {
        return new CouponPolicyCategoryResponseDTO(
                couponPolicyCategory.getCouponPolicyCategoryId(),
                couponPolicyCategory.getCouponPolicy().getCouponPolicyId(),
                couponPolicyCategory.getCategoryId()
        );
    }
}
