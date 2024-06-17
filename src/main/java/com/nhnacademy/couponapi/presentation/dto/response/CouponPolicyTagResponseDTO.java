package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

@Builder
public record CouponPolicyTagResponseDTO(
        Long couponPolicyTagId,
        Long couponPolicyId,
        Long tagId
) {}
