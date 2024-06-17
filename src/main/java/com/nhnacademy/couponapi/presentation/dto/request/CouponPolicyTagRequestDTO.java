package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyTagRequestDTO {
    private Long couponPolicyId;
    private Long tagId;
}
