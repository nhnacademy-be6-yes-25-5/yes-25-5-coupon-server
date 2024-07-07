package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CouponCreationUtilTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponCreationUtil couponCreationUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCoupon() {
        // given
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
                .couponPolicyMaxAmount(new BigDecimal("100.00"))
                .couponPolicyDiscountType(true)
                .build();

        Coupon savedCoupon = Coupon.builder()
                .couponId(1L)
                .couponName("Test Policy")
                .couponCode(UUID.randomUUID().toString())
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        when(couponRepository.save(any(Coupon.class))).thenReturn(savedCoupon);

        // when
        CouponResponseDTO responseDTO = couponCreationUtil.createCoupon(couponPolicy);

        // then
        assertEquals(savedCoupon.getCouponId(), responseDTO.couponId());
        assertEquals(savedCoupon.getCouponName(), responseDTO.couponName());
        assertEquals(savedCoupon.getCouponCode(), responseDTO.couponCode());
        assertEquals(savedCoupon.getCouponExpiredAt(), responseDTO.couponExpiredAt());
        assertEquals(savedCoupon.getCouponCreatedAt(), responseDTO.couponCreatedAt());
        assertEquals(savedCoupon.getCouponPolicy().getCouponPolicyId(), responseDTO.couponPolicyId());

        verify(couponRepository, times(1)).save(any(Coupon.class));
    }
}