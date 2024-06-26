//package com.nhnacademy.couponapi.presentation.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.couponapi.application.service.CouponPolicyService;
//import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
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
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CouponPolicyControllerTest {
//
//    @Mock
//    private CouponPolicyService couponPolicyService;
//
//    @InjectMocks
//    private CouponPolicyController couponPolicyController;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        CouponPolicyResponseDTO couponPolicy = CouponPolicyResponseDTO.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Test Policy")
//                .couponPolicyDiscountValue(BigDecimal.valueOf(10.0))
//                .build();
//        List<CouponPolicyResponseDTO> couponPolicies = Collections.singletonList(couponPolicy);
//
//        when(couponPolicyService.findAllCouponPolicies()).thenReturn(couponPolicies);
//
//        mockMvc.perform(get("/admin-policy"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].couponPolicyId").value(1L))
//                .andExpect(jsonPath("$[0].couponPolicyName").value("Test Policy"));
//    }
//
////    @Test
////    public void testFind() throws Exception {
////        CouponPolicyResponseDTO couponPolicy = CouponPolicyResponseDTO.builder()
////                .couponPolicyId(1L)
////                .couponPolicyName("Test Policy")
////                .couponPolicyDiscountValue(BigDecimal.valueOf(10.0))
////                .build();
////
////        when(couponPolicyService.findCouponPolicyById(1L)).thenReturn(couponPolicy);
////
////        mockMvc.perform(get("/admin-policy/1"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.couponPolicyId").value(1L))
////                .andExpect(jsonPath("$.couponPolicyName").value("Test Policy"));
////    }
//
//    @Test
//    public void testCreate() throws Exception {
//        CouponPolicyRequestDTO requestDTO = new CouponPolicyRequestDTO(
//                "Test Policy",
//                BigDecimal.valueOf(10.0),
//                BigDecimal.valueOf(0.1),
//                BigDecimal.valueOf(50.0),
//                BigDecimal.valueOf(100.0),
//                true
//        );
//        CouponPolicyResponseDTO responseDTO = CouponPolicyResponseDTO.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Test Policy")
//                .couponPolicyDiscountValue(BigDecimal.valueOf(10.0))
//                .build();
//
//        when(couponPolicyService.createCouponPolicy(any(CouponPolicyRequestDTO.class))).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/admin-policy/coupon")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.couponPolicyId").value(1L))
//                .andExpect(jsonPath("$.couponPolicyName").value("Test Policy"));
//    }
//
////    @Test
////    public void testUpdate() throws Exception {
////        CouponPolicyRequestDTO requestDTO = new CouponPolicyRequestDTO(
////                "Updated Policy",
////                BigDecimal.valueOf(20.0),
////                BigDecimal.valueOf(0.2),
////                BigDecimal.valueOf(100.0),
////                BigDecimal.valueOf(200.0),
////                false
////        );
////        CouponPolicyResponseDTO responseDTO = CouponPolicyResponseDTO.builder()
////                .couponPolicyId(1L)
////                .couponPolicyName("Updated Policy")
////                .couponPolicyDiscountValue(BigDecimal.valueOf(20.0))
////                .build();
////
////        when(couponPolicyService.updateCouponPolicy(eq(1L), any(CouponPolicyRequestDTO.class))).thenReturn(responseDTO);
////
////        mockMvc.perform(put("/admin-policy/1")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(requestDTO)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.couponPolicyId").value(1L))
////                .andExpect(jsonPath("$.couponPolicyName").value("Updated Policy"));
////    }
////
////    @Test
////    public void testDelete() throws Exception {
////        doNothing().when(couponPolicyService).deleteCouponPolicy(1L);
////
////        mockMvc.perform(delete("/admin-policy/1"))
////                .andExpect(status().isNoContent());
////
////        verify(couponPolicyService, times(1)).deleteCouponPolicy(1L);
////    }
//}
