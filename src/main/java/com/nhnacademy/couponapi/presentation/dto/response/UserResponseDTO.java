package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Long userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private Date userBirth;
    private Date userRegistrationDate;
    private Date userLastLoginDate;
    private String userPassword;
}
