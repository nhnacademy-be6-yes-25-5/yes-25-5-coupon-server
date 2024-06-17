package com.nhnacademy.couponapi.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CouponPolicyRequestDTO {
    private String couponPolicyName;
    private BigDecimal couponPolicyDiscountValue;
    private Date couponPolicyCreatedAt;
    private Date couponPolicyUpdatedAt;
    private BigDecimal couponPolicyRate;
    private BigDecimal couponPolicyMinOrderAmount;
    private BigDecimal couponPolicyMaxAmount;
    private boolean couponPolicyDiscountType;
}
