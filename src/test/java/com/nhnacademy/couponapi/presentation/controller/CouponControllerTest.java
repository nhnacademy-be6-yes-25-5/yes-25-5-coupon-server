package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
import com.nhnacademy.couponapi.infrastructure.adapter.BookAdapter;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.ExpiredCouponUserResponse;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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

    @Test
    void testGetCoupons_EmptyList() {
        Long bookId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        when(couponService.getCouponsByBookIdAndCategoryIds(anyLong(), anyList())).thenReturn(Collections.emptyList());

        List<BookDetailCouponResponseDTO> response = couponController.getCoupons(bookId, categoryIds);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testGetCouponExpiredDate() {
        Long couponId = 1L;
        Date expiredDate = new Date();

        when(couponService.getCouponExpiredDate(anyLong())).thenReturn(expiredDate);

        ExpiredCouponUserResponse response = couponController.getCouponExpiredDate(couponId);

        assertNotNull(response);
        assertEquals(expiredDate, response.couponExpiredAt());
    }

    @Test
    void testGetCouponExpiredDate_CouponNotFoundException() {
        Long couponId = 1L;

        when(couponService.getCouponExpiredDate(anyLong())).thenThrow(new CouponNotFoundException("Coupon not found with id: " + couponId));

        Exception exception = assertThrows(CouponNotFoundException.class, () -> couponController.getCouponExpiredDate(couponId));

        assertEquals("Coupon not found with id: " + couponId, exception.getMessage());
    }

    @Test
    void testGetCouponsInfo() {
        List<Long> couponIdList = Arrays.asList(1L, 2L);

        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Policy Name")
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
                .couponPolicyMaxAmount(new BigDecimal("100.00"))
                .couponPolicyDiscountType(true)
                .build();

        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponName("Coupon Name")
                .couponCreatedAt(new Date())
                .couponCode("ABC123")
                .couponPolicy(couponPolicy)
                .build();

        when(couponService.getCouponsInfo(anyList())).thenReturn(Arrays.asList(coupon));

        List<CouponInfoResponse> response = couponController.getCouponsInfo(couponIdList);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Coupon Name", response.get(0).couponName());
        assertEquals(new BigDecimal("50.00"), response.get(0).couponMinAmount());
        assertEquals(new BigDecimal("100.00"), response.get(0).couponMaxAmount());
        assertEquals("ABC123", response.get(0).couponCode());
    }

    @Test
    void testGetCouponsInfo_EmptyList() {
        List<Long> couponIdList = Arrays.asList(1L, 2L);

        when(couponService.getCouponsInfo(anyList())).thenReturn(Collections.emptyList());

        List<CouponInfoResponse> response = couponController.getCouponsInfo(couponIdList);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }
}