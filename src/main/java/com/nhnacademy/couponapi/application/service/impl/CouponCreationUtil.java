package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CouponCreationUtil {

    private final CouponRepository couponRepository;

    public CouponResponseDTO createCoupon(CouponPolicy couponPolicy) {
        Coupon coupon = Coupon.builder()
                .couponName(couponPolicy.getCouponPolicyName())
                .couponCode(UUID.randomUUID().toString())
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))  // 한 달 후 만료
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();
        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponResponseDTO.fromEntity(savedCoupon);
    }
}