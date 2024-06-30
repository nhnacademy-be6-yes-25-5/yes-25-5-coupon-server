//package com.nhnacademy.couponapi.presentation.controller;
//
//import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class UserCouponControllerTest {
//
//    @Mock
//    private UserCouponService userCouponService;
//
//    @InjectMocks
//    private UserCouponController userCouponController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userCouponController).build();
//    }
//
//    @Test
//    public void testFindUserCoupons() throws Exception {
//        CouponUserListResponseDTO userCoupon = CouponUserListResponseDTO.builder()
//                .userCouponId(1L)
//                .userId(1L)
//                .couponId(1L)
//                .couponName("Test Coupon")
//                .couponCode("TEST123")
//                .couponPolicyDiscountValue(new BigDecimal("10.00"))
//                .couponPolicyRate(new BigDecimal("0.1"))
//                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
//                .couponPolicyMaxAmount(new BigDecimal("100.00"))
//                .couponCreatedAt(new Date())
//                .couponExpiredAt(new Date())
//                .userCouponUsedAt(new Date())
//                .build();
//        List<CouponUserListResponseDTO> userCoupons = Collections.singletonList(userCoupon);
//
//        when(userCouponService.findUserCoupons(1L)).thenReturn(userCoupons);
//
//        mockMvc.perform(get("/user-coupons/user")
//                        .param("userId", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].userCouponId").value(1L))
//                .andExpect(jsonPath("$[0].userId").value(1L))
//                .andExpect(jsonPath("$[0].couponId").value(1L))
//                .andExpect(jsonPath("$[0].couponName").value("Test Coupon"))
//                .andExpect(jsonPath("$[0].couponCode").value("TEST123"))
//                .andExpect(jsonPath("$[0].couponPolicyDiscountValue").value(10.00))
//                .andExpect(jsonPath("$[0].couponPolicyRate").value(0.1))
//                .andExpect(jsonPath("$[0].couponPolicyMinOrderAmount").value(50.00))
//                .andExpect(jsonPath("$[0].couponPolicyMaxAmount").value(100.00));
//    }
//}
