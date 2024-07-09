package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponCreationUtilTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponCreationUtil couponCreationUtil;

    private CouponPolicy couponPolicy;

    @BeforeEach
    void setUp() {
        couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
                .couponPolicyMaxAmount(new BigDecimal("100.00"))
                .couponPolicyDiscountType(true)
                .build();
    }

    @Test
    void createCoupon_Success() {
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponName(couponPolicy.getCouponPolicyName())
                .couponCode(UUID.randomUUID().toString())
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO responseDTO = couponCreationUtil.createCoupon(couponPolicy);

        assertNotNull(responseDTO);
        assertEquals(coupon.getCouponId(), responseDTO.couponId());
        assertEquals(coupon.getCouponName(), responseDTO.couponName());
        assertEquals(coupon.getCouponCode(), responseDTO.couponCode());
        assertEquals(coupon.getCouponExpiredAt(), responseDTO.couponExpiredAt());
        assertEquals(coupon.getCouponCreatedAt(), responseDTO.couponCreatedAt());
        assertEquals(couponPolicy.getCouponPolicyId(), responseDTO.couponPolicyId());

        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(couponRepository, times(1)).save(couponCaptor.capture());
        Coupon capturedCoupon = couponCaptor.getValue();
        assertEquals(couponPolicy.getCouponPolicyName(), capturedCoupon.getCouponName());
    }

    @Test
    void createCoupon_NullPolicy_ThrowsCouponNotFoundException() {
        CouponNotFoundException exception = assertThrows(CouponNotFoundException.class, () -> {
            couponCreationUtil.createCoupon(null);
        });

        assertEquals("쿠폰 정책을 찾을 수 없습니다.", exception.getErrorStatus().getMessage());
        assertEquals(404, exception.getErrorStatus().getStatus());
        assertNotNull(exception.getErrorStatus().getTimestamp());
        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorStatus().toHttpStatus());
    }
}