package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponRequestDTO {
    private String couponName;
    private String couponCode;
    private Date couponExpiredAt;
    private Date couponCreatedAt;
    private Long couponPolicyId;
}
