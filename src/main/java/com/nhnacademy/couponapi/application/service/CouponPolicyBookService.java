package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;

import java.util.List;

public interface CouponPolicyBookService {
    List<CouponPolicyBookResponseDTO> getAllCouponPolicyBooks();
    CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO);
}