package com.nhnacademy.couponapi.common.exception;

import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class FeignClientException extends ApplicationException {

    public FeignClientException(
            ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
