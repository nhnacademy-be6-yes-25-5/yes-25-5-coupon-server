package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponPolicyTagResponseDTO {
    private Long couponPolicyTagId;
    private Long couponPolicyId;
    private Long tagId;
}
