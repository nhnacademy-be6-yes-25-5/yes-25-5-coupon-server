package com.nhnacademy.couponapi.common.advice;

import com.nhnacademy.couponapi.common.exception.*;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalRestControllerAdvice {


    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorStatus> handleCouponNotFoundException(CouponNotFoundException ex) {
        log.error("CouponNotFoundException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CouponCreationException.class)
    public ResponseEntity<ErrorStatus> handleCouponCreationException(CouponCreationException ex) {
        log.error("CouponCreationException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CouponPolicyBookServiceException.class)
    public ResponseEntity<ErrorStatus> handleCouponPolicyBookServiceException(CouponPolicyBookServiceException ex) {
        log.error("CouponPolicyBookServiceException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CouponPolicyCategoryServiceException.class)
    public ResponseEntity<ErrorStatus> handleCouponPolicyCategoryServiceException(CouponPolicyCategoryServiceException ex) {
        log.error("CouponPolicyCategoryServiceException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CouponPolicyServiceException.class)
    public ResponseEntity<ErrorStatus> handleCouponPolicyServiceException(CouponPolicyServiceException ex) {
        log.error("CouponPolicyServiceException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CouponServiceException.class)
    public ResponseEntity<ErrorStatus> handleCouponServiceException(CouponServiceException ex) {
        log.error("CouponServiceException: ", ex);
        ErrorStatus errorStatus = new ErrorStatus(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStatus> handleGenericException(Exception ex) {
        log.error("Exception: ", ex);
        ErrorStatus errorStatus = new ErrorStatus("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorStatus, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
