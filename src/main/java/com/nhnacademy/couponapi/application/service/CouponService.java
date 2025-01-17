package com.nhnacademy.couponapi.application.service;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;

import java.util.Date;
import java.util.List;

public interface CouponService {
    CouponResponseDTO createCoupon(Coupon coupon);
    List<BookDetailCouponResponseDTO> getAllByBookIdAndCategoryIds(Long bookId, List<Long> categoryIds);
    Date getCouponExpiredDate(Long couponId);
    void removeExpiredCoupons();
    List<CouponInfoResponse> getAllByCouponIdList(List<Long> couponIdList);
}