package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserCouponResponseDTO {
    private Long userCouponId;
    private Long userId;
    private Long couponId;
    private Date userCouponUsedAt;
}
