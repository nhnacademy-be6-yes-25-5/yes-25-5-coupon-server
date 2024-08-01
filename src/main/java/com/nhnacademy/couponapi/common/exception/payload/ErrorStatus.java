package com.nhnacademy.couponapi.common.exception.payload;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorStatus(String message, int status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public HttpStatus toHttpStatus() {
        return HttpStatus.valueOf(this.status);
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static ErrorStatus toErrorStatus(String message, int status, LocalDateTime timestamp) {
        return new ErrorStatus(message, status, timestamp);
    }
}