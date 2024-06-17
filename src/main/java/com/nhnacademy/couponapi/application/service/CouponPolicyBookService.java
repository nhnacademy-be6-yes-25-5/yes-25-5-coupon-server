package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;

import java.util.List;

public interface CouponPolicyBookService {

    List<CouponPolicyBookResponseDTO> getAllCouponPolicyBooks();
    CouponPolicyBookResponseDTO getCouponPolicyBookById(Long id);
    CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO);
    CouponPolicyBookResponseDTO updateCouponPolicyBook(Long id, CouponPolicyBookRequestDTO requestDTO);
    void deleteCouponPolicyBook(Long id);

}
