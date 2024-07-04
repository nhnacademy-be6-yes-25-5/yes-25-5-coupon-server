package com.nhnacademy.couponapi.common.exception.payload;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class ErrorStatus {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorStatus(String message, int status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    // 기존 메서드들

    // toHttpStatus 메서드 추가
    public HttpStatus toHttpStatus() {
        return HttpStatus.valueOf(this.status);
    }

    // Getter 메서드들
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // toErrorStatus 메서드 추가
    public static ErrorStatus toErrorStatus(String message, int status, LocalDateTime timestamp) {
        return new ErrorStatus(message, status, timestamp);
    }
}