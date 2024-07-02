package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.infrastructure.adapter.BookAdapter;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class CouponControllerTest {

    @Mock
    private CouponService couponService;

    @Mock
    private BookAdapter bookAdapter;

    @InjectMocks
    private CouponController couponController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCoupons() {
        Long bookId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Policy Name")
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .build();

        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponName("Coupon Name")
                .couponExpiredAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        when(couponService.getCouponsByBookIdAndCategoryIds(anyLong(), anyList())).thenReturn(Arrays.asList(coupon));

        List<BookDetailCouponResponseDTO> response = couponController.getCoupons(bookId, categoryIds);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Coupon Name", response.get(0).getCouponName());
        assertEquals("Policy Name", response.get(0).getCouponPolicyName());
        assertEquals(new BigDecimal("10.00"), response.get(0).getCouponPolicyDiscountValue());
        assertEquals(new BigDecimal("0.10"), response.get(0).getCouponPolicyRate());
    }
}