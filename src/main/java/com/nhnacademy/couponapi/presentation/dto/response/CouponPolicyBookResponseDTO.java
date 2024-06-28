package com.nhnacademy.couponapi.presentation.dto.response;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import lombok.Builder;

@Builder
public record CouponPolicyBookResponseDTO(
        Long couponPolicyBookId,
        Long bookId,
        String bookName,
        CouponPolicyResponseDTO couponPolicy) {

    public static CouponPolicyBookResponseDTO fromEntity(CouponPolicyBook couponPolicyBook) {
        return new CouponPolicyBookResponseDTO(
                couponPolicyBook.getCouponPolicyBookId(),
                couponPolicyBook.getBookId(),
                couponPolicyBook.getBookName(),
                CouponPolicyResponseDTO.fromEntity(couponPolicyBook.getCouponPolicy())
        );
    }
}