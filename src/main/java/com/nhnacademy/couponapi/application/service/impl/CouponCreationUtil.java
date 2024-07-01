package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 쿠폰 정책을 기반으로 쿠폰을 생성하는 유틸리티 클래스입니다.
 * 생성된 쿠폰을 저장하기 위해 {@link CouponRepository}와 상호 작용합니다.
 */
@RequiredArgsConstructor
@Component
public class CouponCreationUtil {

    private final CouponRepository couponRepository;

    /**
     * 주어진 쿠폰 정책을 기반으로 쿠폰을 생성합니다.
     * 쿠폰은 무작위로 생성된 코드, 생성 날짜, 생성 날짜로부터 한 달 후의 만료 날짜를 가집니다.
     *
     * @param couponPolicy 쿠폰을 생성할 때 사용할 정책
     * @return 생성된 쿠폰을 나타내는 {@link CouponResponseDTO}
     */
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