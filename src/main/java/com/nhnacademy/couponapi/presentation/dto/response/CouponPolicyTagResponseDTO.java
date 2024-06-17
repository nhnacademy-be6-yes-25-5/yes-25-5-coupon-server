package com.nhnacademy.couponapi.presentation.dto.response;

public record CouponPolicyTagResponseDTO(
        Long couponPolicyTagId,
        Long couponPolicyId,
        Long tagId
) {}
