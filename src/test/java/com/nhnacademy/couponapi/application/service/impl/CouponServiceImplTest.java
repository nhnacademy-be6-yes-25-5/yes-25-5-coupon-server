package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.*;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponPolicyBookRepository couponPolicyBookRepository;

    @Mock
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCoupon() {
        Coupon coupon = createCoupon();
        Coupon savedCoupon = createCouponWithId(1L);

        when(couponRepository.save(any(Coupon.class))).thenReturn(savedCoupon);

        CouponResponseDTO responseDTO = couponService.createCoupon(coupon);

        assertNotNull(responseDTO);
        assertEquals(savedCoupon.getCouponId(), responseDTO.couponId());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

//    @Test
//    void testCreateCoupon_Exception() {
//        Coupon coupon = createCoupon();
//        when(couponRepository.save(any(Coupon.class))).thenThrow(new RuntimeException("Error"));
//
//        Exception exception = assertThrows(CouponServiceException.class, () ->
//                couponService.createCoupon(coupon));
//
//        assertEquals("쿠폰을 생성할 수 없습니다", exception.getMessage());
//        verify(couponRepository, times(1)).save(any(Coupon.class));
//    }

    @Test
    void testGetCouponsByBookIdAndCategoryIds() {
        Long bookId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        CouponPolicyBook bookPolicy = createCouponPolicyBook(bookId);
        CouponPolicyCategory categoryPolicy1 = createCouponPolicyCategory(1L);
        CouponPolicyCategory categoryPolicy2 = createCouponPolicyCategory(2L);

        when(couponPolicyBookRepository.findByBookId(bookId)).thenReturn(Collections.singletonList(bookPolicy));
        when(couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds)).thenReturn(Arrays.asList(categoryPolicy1, categoryPolicy2));

        List<CouponPolicy> couponPolicies = Arrays.asList(bookPolicy.getCouponPolicy(), categoryPolicy1.getCouponPolicy(), categoryPolicy2.getCouponPolicy());
        Coupon coupon = createCouponWithPolicy(bookPolicy.getCouponPolicy());

        when(couponRepository.findByCouponPolicyIn(couponPolicies)).thenReturn(Collections.singletonList(coupon));

        List<Coupon> coupons = couponService.getCouponsByBookIdAndCategoryIds(bookId, categoryIds);

        assertNotNull(coupons);
        assertEquals(1, coupons.size());
        assertEquals(coupon.getCouponId(), coupons.get(0).getCouponId());
    }

    private Coupon createCoupon() {
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .build();

        return Coupon.builder()
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();
    }

    private Coupon createCouponWithId(Long id) {
        Coupon coupon = createCoupon();
        return Coupon.builder()
                .couponId(id)
                .couponName(coupon.getCouponName())
                .couponCode(coupon.getCouponCode())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .couponCreatedAt(coupon.getCouponCreatedAt())
                .couponPolicy(coupon.getCouponPolicy())
                .build();
    }

    private CouponPolicyBook createCouponPolicyBook(Long bookId) {
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Book Policy")
                .build();

        return CouponPolicyBook.builder()
                .couponPolicyBookId(1L)
                .couponPolicy(couponPolicy)
                .bookId(bookId)
                .bookName("Book Name")
                .build();
    }

    private CouponPolicyCategory createCouponPolicyCategory(Long categoryId) {
        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(2L)
                .couponPolicyName("Category Policy")
                .build();

        return CouponPolicyCategory.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicy(couponPolicy)
                .categoryId(categoryId)
                .categoryName("Category Name")
                .build();
    }

    private Coupon createCouponWithPolicy(CouponPolicy couponPolicy) {
        return Coupon.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();
    }
}