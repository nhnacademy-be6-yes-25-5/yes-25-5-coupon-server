package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class CouponCreationException extends ApplicationException {
    public CouponCreationException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
