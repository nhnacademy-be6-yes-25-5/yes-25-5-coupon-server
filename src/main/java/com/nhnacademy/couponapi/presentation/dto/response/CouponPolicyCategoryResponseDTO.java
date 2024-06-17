package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponPolicyCategoryResponseDTO {
    private Long couponPolicyCategoryId;
    private Long couponPolicyId;
    private Long categoryId;
}
