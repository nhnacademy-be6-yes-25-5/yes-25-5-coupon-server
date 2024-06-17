package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CouponResponseDTO {
    private Long couponId;
    private String couponName;
    private String couponCode;
    private Date couponExpiredAt;
    private Date couponCreatedAt;
    private Long couponPolicyId;
}
