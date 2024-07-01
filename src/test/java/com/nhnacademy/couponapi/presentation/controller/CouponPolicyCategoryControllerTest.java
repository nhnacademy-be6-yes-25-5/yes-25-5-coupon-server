package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CouponPolicyCategoryControllerTest {

    @Mock
    private CouponPolicyCategoryService couponPolicyCategoryService;

    @InjectMocks
    private CouponPolicyCategoryController couponPolicyCategoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyCategoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testFindAll() throws Exception {
        CouponPolicyCategoryResponseDTO responseDTO = createCouponPolicyCategoryResponseDTO();
        List<CouponPolicyCategoryResponseDTO> responseList = Collections.singletonList(responseDTO);

        when(couponPolicyCategoryService.findAllCouponPolicyCategories()).thenReturn(responseList);

        mockMvc.perform(get("/coupons/policy/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseList)));
    }

    @Test
    void testCreate() throws Exception {
        CouponPolicyCategoryRequestDTO requestDTO = createCouponPolicyCategoryRequestDTO();
        CouponPolicyCategoryResponseDTO responseDTO = createCouponPolicyCategoryResponseDTO();

        when(couponPolicyCategoryService.createCouponPolicyCategory(any(CouponPolicyCategoryRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupons/policy/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    private CouponPolicyCategoryRequestDTO createCouponPolicyCategoryRequestDTO() {
        return new CouponPolicyCategoryRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.10),
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true,
                "Test Category",
                1L
        );
    }

    private CouponPolicyCategoryResponseDTO createCouponPolicyCategoryResponseDTO() {
        return CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(1L)
                .categoryId(1L)
                .categoryName("Test Category")
                .couponPolicy(
                        CouponPolicyResponseDTO.builder()
                                .couponPolicyId(1L)
                                .couponPolicyName("Test Policy")
                                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                                .couponPolicyRate(BigDecimal.valueOf(0.10))
                                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                                .couponPolicyDiscountType(true)
                                .build()
                )
                .build();
    }
}