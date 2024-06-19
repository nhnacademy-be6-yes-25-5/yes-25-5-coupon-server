package com.nhnacademy.couponapi.common.exception;


import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class DefaultException extends ApplicationException {

    public DefaultException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
