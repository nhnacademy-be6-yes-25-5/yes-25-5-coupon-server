package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponCreationException;
import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponPolicyBookRepository couponPolicyBookRepository;

    @Mock
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    private Coupon coupon;
    private CouponPolicy couponPolicy;
    private CouponPolicyBook couponPolicyBook;
    private CouponPolicyCategory couponPolicyCategory;

    @BeforeEach
    void setUp() {
        couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .couponPolicyMinOrderAmount(new BigDecimal("100.00"))
                .couponPolicyMaxAmount(new BigDecimal("50.00"))
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyDiscountType(true)
                .couponPolicyBooks(Collections.singletonList(couponPolicyBook))
                .couponPolicyCategories(Collections.singletonList(couponPolicyCategory))
                .build();

        coupon = Coupon.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponExpiredAt(new Date())
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        couponPolicyBook = CouponPolicyBook.builder()
                .couponPolicyBookId(1L)
                .couponPolicy(couponPolicy)
                .bookId(1L)
                .bookName("Test Book")
                .build();

        couponPolicyCategory = CouponPolicyCategory.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicy(couponPolicy)
                .categoryId(10L)
                .categoryName("Test Category")
                .build();
    }

    @Test
    void createCoupon_Success() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO responseDTO = couponService.createCoupon(coupon);

        assertNotNull(responseDTO);
        assertEquals(coupon.getCouponId(), responseDTO.couponId());
        assertEquals(coupon.getCouponName(), responseDTO.couponName());
        assertEquals(coupon.getCouponCode(), responseDTO.couponCode());
        assertEquals(coupon.getCouponExpiredAt(), responseDTO.couponExpiredAt());
        assertEquals(coupon.getCouponCreatedAt(), responseDTO.couponCreatedAt());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    void createCoupon_NullCoupon_ThrowsException() {
        CouponCreationException exception = assertThrows(CouponCreationException.class, () -> {
            couponService.createCoupon(null);
        });

        assertEquals("쿠폰 정보가 비어있습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void getAllByBookIdAndCategoryIds_Success() {
        when(couponPolicyBookRepository.findByBookId(any(Long.class))).thenReturn(Collections.singletonList(couponPolicyBook));
        when(couponPolicyCategoryRepository.findByCategoryIdIn(anyList())).thenReturn(Collections.singletonList(couponPolicyCategory));
        when(couponRepository.findByCouponPolicyIn(anyList())).thenReturn(Collections.singletonList(coupon));

        List<BookDetailCouponResponseDTO> result = couponService.getAllByBookIdAndCategoryIds(1L, Arrays.asList(1L, 2L));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(couponPolicyBookRepository, times(1)).findByBookId(any(Long.class));
        verify(couponPolicyCategoryRepository, times(1)).findByCategoryIdIn(anyList());
        verify(couponRepository, times(1)).findByCouponPolicyIn(anyList());
    }

    @Test
    void getAllByBookIdAndCategoryIds_NullBookIdOrCategoryIds_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            couponService.getAllByBookIdAndCategoryIds(null, Arrays.asList(1L, 2L));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.getAllByBookIdAndCategoryIds(1L, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.getAllByBookIdAndCategoryIds(1L, Collections.emptyList());
        });
    }

    @Test
    void getCouponExpiredDate_Success() {
        when(couponRepository.findById(any(Long.class))).thenReturn(Optional.of(coupon));

        Date result = couponService.getCouponExpiredDate(1L);

        assertNotNull(result);
        assertEquals(coupon.getCouponExpiredAt(), result);
        verify(couponRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void getCouponExpiredDate_CouponNotFound_ThrowsException() {
        when(couponRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        CouponNotFoundException exception = assertThrows(CouponNotFoundException.class, () -> {
            couponService.getCouponExpiredDate(1L);
        });

        assertEquals("해당 ID의 쿠폰을 찾을 수 없습니다: 1", exception.getErrorStatus().getMessage());
    }

    @Test
    void removeExpiredCoupons() {
        doNothing().when(couponRepository).deleteByCouponExpiredAtBefore(any(Date.class));

        couponService.removeExpiredCoupons();

        verify(couponRepository, times(1)).deleteByCouponExpiredAtBefore(any(Date.class));
    }

    @Test
    void getAllByCouponIdList_Success() {
        // Create CouponPolicyBook and CouponPolicyCategory objects
        CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                .couponPolicyBookId(1L)
                .bookId(1L)
                .build();

        CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
                .couponPolicyCategoryId(1L)
                .categoryId(10L)
                .build();

        // Initialize couponPolicyBooks and couponPolicyCategories lists
        List<CouponPolicyBook> couponPolicyBooks = List.of(couponPolicyBook);
        List<CouponPolicyCategory> couponPolicyCategories = List.of(couponPolicyCategory);

        CouponPolicy couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Policy Name")
                .couponPolicyMinOrderAmount(new BigDecimal("100.00"))
                .couponPolicyMaxAmount(new BigDecimal("50.00"))
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyDiscountType(true)
                .couponPolicyBooks(couponPolicyBooks)
                .couponPolicyCategories(couponPolicyCategories)
                .build();

        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponName("Coupon Name")
                .couponCode("ABC123")
                .couponCreatedAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        when(couponRepository.findAllById(anyList())).thenReturn(Collections.singletonList(coupon));

        List<CouponInfoResponse> result = couponService.getAllByCouponIdList(Arrays.asList(1L, 2L));

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(couponRepository, times(1)).findAllById(anyList());

        CouponInfoResponse response = result.get(0);
        assertEquals(coupon.getCouponId(), response.couponId());
        assertEquals(coupon.getCouponName(), response.couponName());
        assertEquals(coupon.getCouponCode(), response.couponCode());
        assertEquals(coupon.getCouponPolicy().getCouponPolicyMinOrderAmount(), response.couponMinAmount());
        assertEquals(coupon.getCouponPolicy().getCouponPolicyMaxAmount(), response.couponMaxAmount());
        assertEquals(coupon.getCouponPolicy().getCouponPolicyDiscountValue(), response.couponDiscountAmount());
        assertEquals(coupon.getCouponPolicy().getCouponPolicyRate(), response.couponDiscountRate());
        assertEquals(coupon.getCouponCreatedAt(), response.couponCreatedAt());
        assertEquals(coupon.getCouponPolicy().isCouponPolicyDiscountType(), response.couponDiscountType());
        assertNotNull(response.categoryIds());
        assertNotNull(response.bookId());
        assertNotNull(response.applyCouponToAllBooks());
    }


    @Test
    void getAllByCouponIdList_NullOrEmptyList_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            couponService.getAllByCouponIdList(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.getAllByCouponIdList(Collections.emptyList());
        });
    }
}
