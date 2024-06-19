package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponPolicyCategoryServiceException extends ApplicationException {

    public CouponPolicyCategoryServiceException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
