//package com.nhnacademy.couponapi.presentation.controller;
//
//import com.nhnacademy.couponapi.application.service.CouponService;
//import com.nhnacademy.couponapi.application.service.UserCouponService;
//import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.ReadOrderUserCouponResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CouponControllerTest {
//
//    @Mock
//    private CouponService couponService;
//
//    @Mock
//    private UserCouponService userCouponService;
//
//    @InjectMocks
//    private CouponController couponController;
//
//    private CouponRequestDTO couponRequestDTO;
//    private CouponResponseDTO couponResponseDTO;
//    private CouponUserListResponseDTO couponUserListResponseDTO;
//    private ReadOrderUserCouponResponse readOrderUserCouponResponse;
//
//    @BeforeEach
//    void setUp() {
//        couponRequestDTO = new CouponRequestDTO("Test Coupon", "TEST123", new Date(), 1L);
//        couponResponseDTO = CouponResponseDTO.builder()
//                .couponId(1L)
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponExpiredAt(new Date())
//                .couponCreatedAt(new Date())
//                .couponPolicyId(1L)
//                .build();
//
//        couponUserListResponseDTO = CouponUserListResponseDTO.builder()
//                .userCouponId(1L)
//                .userId(1L)
//                .couponId(1L)
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponPolicyDiscountValue(new BigDecimal("10.00"))
//                .couponPolicyRate(new BigDecimal("0.10"))
//                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
//                .couponPolicyMaxAmount(new BigDecimal("100.00"))
//                .couponCreatedAt(new Date())
//                .couponExpiredAt(new Date())
//                .userCouponUsedAt(new Date())
//                .build();
//
//        readOrderUserCouponResponse = new ReadOrderUserCouponResponse(1L, new BigDecimal("10.00"));
//    }
//
//    @Test
//    public void testFindAll() {
//        when(couponService.findAllCoupons()).thenReturn(List.of(couponUserListResponseDTO));
//
//        ResponseEntity<List<CouponUserListResponseDTO>> response = couponController.findAll();
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).hasSize(1);
//        assertThat(response.getBody().get(0).couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).findAllCoupons();
//    }
//
//    @Test
//    public void testFind() {
//        when(couponService.findCouponById(anyLong())).thenReturn(couponResponseDTO);
//
//        ResponseEntity<CouponResponseDTO> response = couponController.find(1L);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).findCouponById(1L);
//    }
//
//    @Test
//    public void testCreate() {
//        when(couponService.createCoupon(any(CouponRequestDTO.class))).thenReturn(couponResponseDTO);
//
//        ResponseEntity<CouponResponseDTO> response = couponController.create(couponRequestDTO);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody().couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).createCoupon(couponRequestDTO);
//    }
//
//    @Test
//    public void testUpdate() {
//        when(couponService.updateCoupon(anyLong(), any(CouponRequestDTO.class))).thenReturn(couponResponseDTO);
//
//        ResponseEntity<CouponResponseDTO> response = couponController.update(1L, couponRequestDTO);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).updateCoupon(eq(1L), any(CouponRequestDTO.class));
//    }
//
//    @Test
//    public void testDelete() {
//        doNothing().when(couponService).deleteCoupon(anyLong());
//
//        ResponseEntity<Void> response = couponController.delete(1L);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//        verify(couponService, times(1)).deleteCoupon(1L);
//    }
//
//    @Test
//    public void testIssueWelcomeCoupon() {
//        doNothing().when(userCouponService).issueWelcomeCoupon(anyLong());
//
//        ResponseEntity<Void> response = couponController.issueWelcomeCoupon(1L);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        verify(userCouponService, times(1)).issueWelcomeCoupon(1L);
//    }
//
//    @Test
//    public void testGetCouponsByBookId() {
//        when(couponService.getCouponsByBookId(anyLong())).thenReturn(List.of(couponUserListResponseDTO));
//
//        ResponseEntity<List<CouponUserListResponseDTO>> response = couponController.getCouponsByBookId(1L);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).hasSize(1);
//        assertThat(response.getBody().get(0).couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).getCouponsByBookId(1L);
//    }
//
//    @Test
//    public void testGetCouponsByCategoryIds() {
//        when(couponService.getCouponsByCategoryIds(anyList())).thenReturn(List.of(couponUserListResponseDTO));
//
//        ResponseEntity<List<CouponUserListResponseDTO>> response = couponController.getCouponsByCategoryIds(List.of(1L, 2L));
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).hasSize(1);
//        assertThat(response.getBody().get(0).couponId()).isEqualTo(1L);
//
//        verify(couponService, times(1)).getCouponsByCategoryIds(anyList());
//    }
//
////    @Test
////    public void testGetBestCoupon() {
////        Authentication authentication = mock(Authentication.class);
////        SecurityContext securityContext = mock(SecurityContext.class);
////        when(securityContext.getAuthentication()).thenReturn(authentication);
////        SecurityContextHolder.setContext(securityContext);
////
////        when(authentication.getPrincipal()).thenReturn(1L);
////        when(couponService.findBestCoupon(anyLong(), any(BigDecimal.class))).thenReturn(readOrderUserCouponResponse);
////
//////        ResponseEntity<ReadOrderUserCouponResponse> response = couponController.getBestCoupon(new BigDecimal("100.00"));
////
//////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//////        assertThat(response.getBody().couponId()).isEqualTo(1L);
//////        assertThat(response.getBody().discountAmount()).isEqualTo(new BigDecimal("10.00"));
////
////        verify(couponService, times(1)).findBestCoupon(eq(1L), any(BigDecimal.class));
////    }
//}