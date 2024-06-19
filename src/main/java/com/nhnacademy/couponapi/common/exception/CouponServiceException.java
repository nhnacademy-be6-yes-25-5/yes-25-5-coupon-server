package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponServiceException extends ApplicationException {

    public CouponServiceException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
