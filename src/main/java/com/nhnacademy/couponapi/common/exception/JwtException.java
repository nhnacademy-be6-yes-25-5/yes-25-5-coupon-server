package com.nhnacademy.couponapi.common.exception;


import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;

public class JwtException extends ApplicationException {

    public JwtException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
