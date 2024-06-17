package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

@Builder
public record CouponPolicyCategoryResponseDTO(
        Long couponPolicyCategoryId,
        Long couponPolicyId,
        Long categoryId
) {}
