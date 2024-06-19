package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class UserCouponServiceException extends ApplicationException {

    public UserCouponServiceException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
