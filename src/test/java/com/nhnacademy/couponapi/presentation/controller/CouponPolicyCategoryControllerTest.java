package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CouponPolicyCategoryControllerTest {

    @Mock
    private CouponPolicyCategoryService couponPolicyCategoryService;

    @InjectMocks
    private CouponPolicyCategoryController couponPolicyCategoryController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyCategoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        CouponPolicyCategoryResponseDTO couponPolicyCategory = CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicyId(1L)
                .categoryId(1L)
                .build();
        List<CouponPolicyCategoryResponseDTO> couponPolicyCategories = Collections.singletonList(couponPolicyCategory);

        when(couponPolicyCategoryService.findAllCouponPolicyCategories()).thenReturn(couponPolicyCategories);

        mockMvc.perform(get("/coupon-policy-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].couponPolicyCategoryId").value(1L));
    }

    @Test
    public void testFind() throws Exception {
        CouponPolicyCategoryResponseDTO couponPolicyCategory = CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicyId(1L)
                .categoryId(1L)
                .build();

        when(couponPolicyCategoryService.findCouponPolicyCategoryById(1L)).thenReturn(couponPolicyCategory);

        mockMvc.perform(get("/coupon-policy-categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyCategoryId").value(1L));
    }

    @Test
    public void testCreate() throws Exception {
        CouponPolicyCategoryRequestDTO requestDTO = new CouponPolicyCategoryRequestDTO(
                1L,
                1L
        );
        CouponPolicyCategoryResponseDTO responseDTO = CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicyId(1L)
                .categoryId(1L)
                .build();

        when(couponPolicyCategoryService.createCouponPolicyCategory(any(CouponPolicyCategoryRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupon-policy-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponPolicyCategoryId").value(1L));
    }

    @Test
    public void testUpdate() throws Exception {
        CouponPolicyCategoryRequestDTO requestDTO = new CouponPolicyCategoryRequestDTO(
                1L,
                1L
        );
        CouponPolicyCategoryResponseDTO responseDTO = CouponPolicyCategoryResponseDTO.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicyId(1L)
                .categoryId(1L)
                .build();

        when(couponPolicyCategoryService.updateCouponPolicyCategory(eq(1L), any(CouponPolicyCategoryRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/coupon-policy-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyCategoryId").value(1L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(couponPolicyCategoryService).deleteCouponPolicyCategory(1L);

        mockMvc.perform(delete("/coupon-policy-categories/1"))
                .andExpect(status().isNoContent());

        verify(couponPolicyCategoryService, times(1)).deleteCouponPolicyCategory(1L);
    }
}
