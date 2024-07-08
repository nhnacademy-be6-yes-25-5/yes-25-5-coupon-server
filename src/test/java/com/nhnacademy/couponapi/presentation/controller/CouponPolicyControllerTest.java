package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponPolicyControllerTest {

    @Mock
    private CouponPolicyService couponPolicyService;

    @InjectMocks
    private CouponPolicyController couponPolicyController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        CouponPolicyResponseDTO responseDTO = createCouponPolicyResponseDTO();
        List<CouponPolicyResponseDTO> responseList = Collections.singletonList(responseDTO);

        when(couponPolicyService.getAllCouponPolicies()).thenReturn(responseList);

        mockMvc.perform(get("/coupons/policy"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseList)));
    }

    @Test
    void testCreate() throws Exception {
        CouponPolicyRequestDTO requestDTO = new CouponPolicyRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                null, // No couponPolicyRate
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true
        );

        CouponPolicyResponseDTO responseDTO = createCouponPolicyResponseDTO();

        when(couponPolicyService.createCouponPolicy(any(CouponPolicyRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupons/policy/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    private CouponPolicyRequestDTO createCouponPolicyRequestDTO() {
        return new CouponPolicyRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                null,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true
        );
    }

    private CouponPolicyResponseDTO createCouponPolicyResponseDTO() {
        return CouponPolicyResponseDTO.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                .couponPolicyCreatedAt(new Date())
                .couponPolicyUpdatedAt(new Date())
                .couponPolicyRate(BigDecimal.valueOf(0.10))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                .couponPolicyDiscountType(true)
                .build();
    }
}