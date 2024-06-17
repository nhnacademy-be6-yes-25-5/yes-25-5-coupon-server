package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyBookRequestDTO {
    private Long couponPolicyId;
    private Long bookId;
}
