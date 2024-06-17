package com.nhnacademy.couponapi.presentation.dto.response;

import java.util.Date;

public record UserResponseDTO(
        Long userId,
        String userName,
        String userPhone,
        String userEmail,
        Date userBirth,
        Date userRegistrationDate,
        Date userLastLoginDate,
        String userPassword
) {}
