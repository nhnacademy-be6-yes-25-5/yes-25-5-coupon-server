package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

@Builder
public record JwtAuthResponse(Long customerId,
                              String role,
                              String loginStateName) {

}
