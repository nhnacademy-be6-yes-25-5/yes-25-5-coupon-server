//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.application.adapter.UserAdapter;
//import com.nhnacademy.couponapi.application.service.CouponService;
//import com.nhnacademy.couponapi.common.exception.FeignClientException;
//import com.nhnacademy.couponapi.common.exception.UserCouponServiceException;
//import com.nhnacademy.couponapi.persistence.domain.Coupon;
//import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
//import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserCouponServiceImplTest {
//
//    @Mock
//    private UserCouponRepository userCouponRepository;
//
//    @Mock
//    private CouponService couponService;
//
//    @Mock
//    private UserAdapter userAdapter;
//
//    @InjectMocks
//    private UserCouponServiceImpl userCouponService;
//
//    private Coupon coupon;
//    private UserCoupon userCoupon;
//
//    @BeforeEach
//    public void setUp() {
//        coupon = Coupon.builder()
//                .couponId(1L)
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponExpiredAt(new Date())
//                .couponPolicy(null)
//                .build();
//
//        userCoupon = UserCoupon.builder()
//                .userCouponId(1L)
//                .userId(1L)
//                .coupon(coupon)
//                .userCouponUsedAt(new Date())
//                .build();
//    }
//
//    @Test
//    public void testFindUserCoupons() {
//        when(userCouponRepository.findByUserId(1L)).thenReturn(Collections.singletonList(userCoupon));
//
//        List<CouponUserListResponseDTO> result = userCouponService.findUserCoupons(1L);
//
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0).couponId()).isEqualTo(1L);
//    }
//
//    @Test
//    public void testDeleteUserCoupon() {
//        doNothing().when(userCouponRepository).deleteById(1L);
//
//        userCouponService.deleteUserCoupon(1L);
//
//        verify(userCouponRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void testDeleteUserCoupon_Exception() {
//        doThrow(new RuntimeException("Delete failed")).when(userCouponRepository).deleteById(1L);
//
//        assertThrows(UserCouponServiceException.class, () -> {
//            userCouponService.deleteUserCoupon(1L);
//        });
//    }
//
//    @Test
//    public void testIssueBirthdayCoupons_Exception() {
//        when(userAdapter.findAllUsers()).thenThrow(new RuntimeException("Feign client exception"));
//
//        assertThrows(FeignClientException.class, () -> {
//            userCouponService.issueBirthdayCoupons();
//        });
//    }
//
//    @Test
//    public void testIssueWelcomeCoupon_WithRetry_Exception() {
//        doThrow(new RuntimeException("Service exception"))
//                .doThrow(new RuntimeException("Service exception"))
//                .doThrow(new RuntimeException("Service exception"))
//                .when(couponService).issueWelcomeCoupon(anyLong());
//
//        assertThrows(FeignClientException.class, () -> {
//            userCouponService.issueWelcomeCoupon(1L);
//        });
//
//        verify(couponService, times(3)).issueWelcomeCoupon(1L);
//    }
//}