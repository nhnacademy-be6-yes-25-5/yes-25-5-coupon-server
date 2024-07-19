package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponNotFoundException extends ApplicationException {

    public CouponNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}