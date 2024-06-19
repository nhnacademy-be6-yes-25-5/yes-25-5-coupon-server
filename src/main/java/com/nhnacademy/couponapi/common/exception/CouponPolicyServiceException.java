package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponPolicyServiceException extends ApplicationException {

    public CouponPolicyServiceException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
