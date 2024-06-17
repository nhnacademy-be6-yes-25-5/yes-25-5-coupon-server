package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

@Builder
public record CouponPolicyBookResponseDTO(
        Long couponPolicyBookId,
        Long couponPolicyId,
        Long bookId
) {}
