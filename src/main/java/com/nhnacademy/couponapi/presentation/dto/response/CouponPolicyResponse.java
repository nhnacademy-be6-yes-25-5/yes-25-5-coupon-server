package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistance.domain.CouponPolicy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponPolicyResponse {
    private Long id;
    private String policyName;
    private int discountValue;

    public CouponPolicyResponse(CouponPolicy policy) {
        this.id = policy.getId();
        this.policyName = policy.getPolicyName();
        this.discountValue = policy.getDiscountValue();
    }

    // getters and setters
}
