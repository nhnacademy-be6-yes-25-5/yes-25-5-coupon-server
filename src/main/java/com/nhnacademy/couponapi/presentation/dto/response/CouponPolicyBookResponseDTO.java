package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;

public record CouponPolicyBookResponseDTO(
        Long couponPolicyBookId,
        Long couponPolicyId,
        Long bookId) {

    public static CouponPolicyBookResponseDTO fromEntity(CouponPolicyBook couponPolicyBook) {
        return new CouponPolicyBookResponseDTO(
                couponPolicyBook.getCouponPolicyBookId(),
                couponPolicyBook.getCouponPolicy().getCouponPolicyId(),
                couponPolicyBook.getBookId()
        );
    }
}