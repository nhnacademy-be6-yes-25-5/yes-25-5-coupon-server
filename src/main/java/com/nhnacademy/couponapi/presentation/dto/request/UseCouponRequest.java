package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UseCouponRequest {
    private Long couponId;
    private Long userId;

    // getters and setters
}
