package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponPolicyBookServiceException extends ApplicationException {

    public CouponPolicyBookServiceException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
