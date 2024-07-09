//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.common.exception.CouponCreationException;
//import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
//import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
//import com.nhnacademy.couponapi.persistence.domain.Coupon;
//import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
//import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CouponCreationUtilTest {
//
//    @Mock
//    private CouponRepository couponRepository;
//
//    private CouponCreationUtil couponCreationUtil;
//
//    @BeforeEach
//    public void setUp() {
//        couponCreationUtil = new CouponCreationUtil(couponRepository);
//    }
//
//    @Test
//    public void testCreateCoupon_SuccessfulCreation() {
//        // Given
//        CouponPolicy couponPolicy = createCouponPolicy();
//        Coupon coupon = createCouponEntity(couponPolicy);
//        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);
//
//        // When
//        CouponResponseDTO responseDTO = couponCreationUtil.createCoupon(couponPolicy);
//
//        // Then
//        assertNotNull(responseDTO);
//        assertEquals(coupon.getCouponName(), responseDTO.couponName());
//        assertEquals(coupon.getCouponCode(), responseDTO.couponCode());
//        assertEquals(coupon.getCouponExpiredAt(), responseDTO.couponExpiredAt());
//        assertEquals(coupon.getCouponCreatedAt(), responseDTO.couponCreatedAt());
//        assertEquals(coupon.getCouponPolicy().getCouponPolicyId(), responseDTO.couponPolicyId());
//
//        verify(couponRepository, times(1)).save(any(Coupon.class));
//    }
//
//    @Test
//    public void testCreateCoupon_NullCouponPolicy() {
//        // When & Then
//        CouponNotFoundException exception = assertThrows(CouponNotFoundException.class,
//                () -> couponCreationUtil.createCoupon(null));
//        assertEquals("쿠폰 정책을 찾을 수 없습니다.", exception.getMessage());
//        assertEquals(404, exception.getErrorStatus().getStatus());
//        assertNotNull(exception.getErrorStatus().getTimestamp());
//
//        verify(couponRepository, never()).save(any(Coupon.class));
//    }
//
////    @Test
////    public void testCreateCoupon_CouponCreationException() {
////        // Given
////        CouponPolicy couponPolicy = createCouponPolicy();
////        when(couponRepository.save(any(Coupon.class))).thenReturn(null);
////
////        // When & Then
////        CouponCreationException exception = assertThrows(CouponCreationException.class,
////                () -> couponCreationUtil.createCoupon(couponPolicy));
////        assertEquals("쿠폰 생성 중 오류가 발생했습니다.", exception.getMessage());
////        assertEquals(500, exception.getErrorStatus().getStatus());
////        assertNotNull(exception.getErrorStatus().getTimestamp());
////
////        verify(couponRepository, times(1)).save(any(Coupon.class));
////    }
//
//    private CouponPolicy createCouponPolicy() {
//        return CouponPolicy.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Test Coupon Policy")
//                .couponPolicyMinOrderAmount(BigDecimal.valueOf(100))
//                .couponPolicyMaxAmount(BigDecimal.valueOf(500))
//                .build();
//    }
//
//    private Coupon createCouponEntity(CouponPolicy couponPolicy) {
//        return Coupon.builder()
//                .couponId(1L)
//                .couponName(couponPolicy.getCouponPolicyName())
//                .couponCode("abcd-efgh-ijkl")
//                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
//                .couponCreatedAt(new Date())
//                .couponPolicy(couponPolicy)
//                .build();
//    }
//}
