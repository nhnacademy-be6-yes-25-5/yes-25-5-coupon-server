package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCouponRequestDTO {
    private Long userId;
    private Long couponId;
    private Date userCouponUsedAt;
}
