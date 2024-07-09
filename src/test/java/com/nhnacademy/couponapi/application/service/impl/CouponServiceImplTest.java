//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
//import com.nhnacademy.couponapi.common.exception.CouponServiceException;
//import com.nhnacademy.couponapi.persistence.domain.*;
//import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
//import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
//import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.dao.DataAccessException;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CouponServiceImplTest {
//
//    @Mock
//    private CouponRepository couponRepository;
//
//    @Mock
//    private CouponPolicyBookRepository couponPolicyBookRepository;
//
//    @Mock
//    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;
//
//    @InjectMocks
//    private CouponServiceImpl couponService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateCoupon() {
//        Coupon coupon = createCoupon();
//        Coupon savedCoupon = createCouponWithId(1L);
//
//        when(couponRepository.save(any(Coupon.class))).thenReturn(savedCoupon);
//
//        CouponResponseDTO responseDTO = couponService.createCoupon(coupon);
//
//        assertNotNull(responseDTO);
//        assertEquals(savedCoupon.getCouponId(), responseDTO.couponId());
//        verify(couponRepository, times(1)).save(any(Coupon.class));
//    }
//
////    @Test
////    void testCreateCoupon_DataAccessException() {
////        Coupon coupon = createCoupon();
////
////        when(couponRepository.save(any(Coupon.class))).thenThrow(new DataAccessException("DB error") {
////        });
////
////        CouponServiceException exception = assertThrows(CouponServiceException.class, () ->
////                couponService.createCoupon(coupon));
////
////        assertEquals("쿠폰을 생성할 수 없습니다", exception.getErrorStatus().getMessage());
////        verify(couponRepository, times(1)).save(any(Coupon.class));
////    }
//
//    @Test
//    void testGetAllByBookIdAndCategoryIds() {
//        Long bookId = 1L;
//        List<Long> categoryIds = Arrays.asList(1L, 2L);
//
//        CouponPolicyBook bookPolicy = createCouponPolicyBook(bookId);
//        CouponPolicyCategory categoryPolicy1 = createCouponPolicyCategory(1L);
//        CouponPolicyCategory categoryPolicy2 = createCouponPolicyCategory(2L);
//
//        when(couponPolicyBookRepository.findByBookId(bookId)).thenReturn(Collections.singletonList(bookPolicy));
//        when(couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds)).thenReturn(Arrays.asList(categoryPolicy1, categoryPolicy2));
//
//        List<CouponPolicy> couponPolicies = Arrays.asList(bookPolicy.getCouponPolicy(), categoryPolicy1.getCouponPolicy(), categoryPolicy2.getCouponPolicy());
//        Coupon coupon = createCouponWithPolicy(bookPolicy.getCouponPolicy());
//
//        when(couponRepository.findByCouponPolicyIn(couponPolicies)).thenReturn(Collections.singletonList(coupon));
//
//        List<Coupon> coupons = couponService.getAllByBookIdAndCategoryIds(bookId, categoryIds);
//
//        assertNotNull(coupons);
//        assertEquals(1, coupons.size());
//        assertEquals(coupon.getCouponId(), coupons.get(0).getCouponId());
//    }
//
////    @Test
////    void testGetAllByBookIdAndCategoryIds_Exception() {
////        Long bookId = 1L;
////        List<Long> categoryIds = Arrays.asList(1L, 2L);
////
////        when(couponPolicyBookRepository.findByBookId(bookId)).thenThrow(new RuntimeException("Unknown error"));
////
////        CouponServiceException exception = assertThrows(CouponServiceException.class, () ->
////                couponService.getAllByBookIdAndCategoryIds(bookId, categoryIds));
////
////        assertEquals("도서 ID와 카테고리 ID 목록에 해당하는 쿠폰 조회 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
////    }
//
//    @Test
//    void testGetCouponExpiredDate() {
//        Long couponId = 1L;
//        Coupon coupon = createCouponWithId(couponId);
//        when(couponRepository.findById(couponId)).thenReturn(java.util.Optional.of(coupon));
//
//        Date expiredDate = couponService.getCouponExpiredDate(couponId);
//
//        assertNotNull(expiredDate);
//        assertEquals(coupon.getCouponExpiredAt(), expiredDate);
//        verify(couponRepository, times(1)).findById(couponId);
//    }
//
//    @Test
//    void testGetCouponExpiredDate_CouponNotFoundException() {
//        Long couponId = 1L;
//        when(couponRepository.findById(couponId)).thenReturn(java.util.Optional.empty());
//
//        Exception exception = assertThrows(CouponNotFoundException.class, () ->
//                couponService.getCouponExpiredDate(couponId));
//
//        assertEquals("해당 ID의 쿠폰을 찾을 수 없습니다: " + couponId, exception.getMessage());
//        verify(couponRepository, times(1)).findById(couponId);
//    }
//
////    @Test
////    void testGetCouponExpiredDate_Exception() {
////        Long couponId = 1L;
////        when(couponRepository.findById(couponId)).thenThrow(new RuntimeException("Unknown error"));
////
////        CouponServiceException exception = assertThrows(CouponServiceException.class, () ->
////                couponService.getCouponExpiredDate(couponId));
////
////        assertEquals("쿠폰 만료 날짜 조회 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
////    }
//
//    @Test
//    void testRemoveExpiredCoupons() {
//        Date now = new Date();
//        couponService.removeExpiredCoupons();
//
//        verify(couponRepository, times(1)).deleteByCouponExpiredAtBefore(now);
//    }
//
////    @Test
////    void testRemoveExpiredCoupons_Exception() {
////        Date now = new Date();
////        doThrow(new RuntimeException("Unknown error")).when(couponRepository).deleteByCouponExpiredAtBefore(any(Date.class));
////
////        CouponServiceException exception = assertThrows(CouponServiceException.class, () ->
////                couponService.removeExpiredCoupons());
////
////        assertEquals("만료된 쿠폰 삭제 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
////    }
//
//    @Test
//    void testGetAllByCouponIdList() {
//        List<Long> couponIdList = Arrays.asList(1L, 2L);
//        Coupon coupon1 = createCouponWithId(1L);
//        Coupon coupon2 = createCouponWithId(2L);
//
//        when(couponRepository.findAllById(couponIdList)).thenReturn(Arrays.asList(coupon1, coupon2));
//
//        List<Coupon> coupons = couponService.getAllByCouponIdList(couponIdList);
//
//        assertNotNull(coupons);
//        assertEquals(2, coupons.size());
//        assertEquals(coupon1.getCouponId(), coupons.get(0).getCouponId());
//        assertEquals(coupon2.getCouponId(), coupons.get(1).getCouponId());
//        verify(couponRepository, times(1)).findAllById(couponIdList);
//    }
//
////    @Test
////    void testGetAllByCouponIdList_Exception() {
////        List<Long> couponIdList = Arrays.asList(1L, 2L);
////
////        when(couponRepository.findAllById(couponIdList)).thenThrow(new RuntimeException("Unknown error"));
////
////        CouponServiceException exception = assertThrows(CouponServiceException.class, () ->
////                couponService.getAllByCouponIdList(couponIdList));
////
////        assertEquals("쿠폰 ID 목록 조회 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
////    }
//
//    private Coupon createCoupon() {
//        CouponPolicy couponPolicy = CouponPolicy.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Test Policy")
//                .build();
//
//        return Coupon.builder()
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
//                .couponCreatedAt(new Date())
//                .couponPolicy(couponPolicy)
//                .build();
//    }
//
//    private Coupon createCouponWithId(Long id) {
//        Coupon coupon = createCoupon();
//        return Coupon.builder()
//                .couponId(id)
//                .couponName(coupon.getCouponName())
//                .couponCode(coupon.getCouponCode())
//                .couponExpiredAt(coupon.getCouponExpiredAt())
//                .couponCreatedAt(coupon.getCouponCreatedAt())
//                .couponPolicy(coupon.getCouponPolicy())
//                .build();
//    }
//
//    private CouponPolicyBook createCouponPolicyBook(Long bookId) {
//        CouponPolicy couponPolicy = CouponPolicy.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Book Policy")
//                .build();
//
//        return CouponPolicyBook.builder()
//                .couponPolicyBookId(1L)
//                .couponPolicy(couponPolicy)
//                .bookId(bookId)
//                .bookName("Book Name")
//                .build();
//    }
//
//    private CouponPolicyCategory createCouponPolicyCategory(Long categoryId) {
//        CouponPolicy couponPolicy = CouponPolicy.builder()
//                .couponPolicyId(2L)
//                .couponPolicyName("Category Policy")
//                .build();
//
//        return CouponPolicyCategory.builder()
//                .couponPolicyCategoryId(1L)
//                .couponPolicy(couponPolicy)
//                .categoryId(categoryId)
//                .categoryName("Category Name")
//                .build();
//    }
//
//    private Coupon createCouponWithPolicy(CouponPolicy couponPolicy) {
//        return Coupon.builder()
//                .couponId(1L)
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
//                .couponCreatedAt(new Date())
//                .couponPolicy(couponPolicy)
//                .build();
//    }
//}