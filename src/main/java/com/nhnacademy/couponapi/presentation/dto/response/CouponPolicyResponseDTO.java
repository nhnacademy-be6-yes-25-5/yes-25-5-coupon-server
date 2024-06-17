package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class CouponPolicyResponseDTO {
    private Long couponPolicyId;
    private String couponPolicyName;
    private BigDecimal couponPolicyDiscountValue;
    private Date couponPolicyCreatedAt;
    private Date couponPolicyUpdatedAt;
    private BigDecimal couponPolicyRate;
    private BigDecimal couponPolicyMinOrderAmount;
    private BigDecimal couponPolicyMaxAmount;
    private boolean couponPolicyDiscountType;
}
