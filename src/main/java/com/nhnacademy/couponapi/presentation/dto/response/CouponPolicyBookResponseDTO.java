package com.nhnacademy.couponapi.presentation.dto.response;

public record CouponPolicyBookResponseDTO(
        Long couponPolicyBookId,
        Long couponPolicyId,
        Long bookId
) {}
