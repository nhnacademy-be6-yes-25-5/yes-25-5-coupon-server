package com.nhnacademy.couponapi.presentation.dto.response;

public record JwtAuthResponse(Long customerId,
                              String role,
                              String loginStateName,
                              String refreshJwt) {

}