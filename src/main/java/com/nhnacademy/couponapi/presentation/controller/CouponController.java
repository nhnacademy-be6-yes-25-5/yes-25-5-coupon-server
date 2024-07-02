package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.infrastructure.adapter.BookAdapter;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final BookAdapter bookAdapter;

    @GetMapping
    public List<BookDetailCouponResponseDTO> getCoupons(@RequestParam Long bookId, @RequestParam List<Long> categoryIds) {
        List<Coupon> coupons = couponService.getCouponsByBookIdAndCategoryIds(bookId, categoryIds);
        return coupons.stream()
                .map(coupon -> BookDetailCouponResponseDTO.builder()
                        .couponName(coupon.getCouponName())
                        .couponExpiredAt(coupon.getCouponExpiredAt())
                        .couponPolicyName(coupon.getCouponPolicy().getCouponPolicyName())
                        .couponPolicyDiscountValue(coupon.getCouponPolicy().getCouponPolicyDiscountValue())
                        .couponPolicyRate(coupon.getCouponPolicy().getCouponPolicyRate())
                        .build())
                .collect(Collectors.toList());
    }
}