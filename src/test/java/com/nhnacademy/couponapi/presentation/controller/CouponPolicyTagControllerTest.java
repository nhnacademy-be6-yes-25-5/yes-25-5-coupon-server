package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
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
public class CouponPolicyTagControllerTest {

    @Mock
    private CouponPolicyTagService couponPolicyTagService;

    @InjectMocks
    private CouponPolicyTagController couponPolicyTagController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyTagController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        CouponPolicyTagResponseDTO couponPolicyTag = CouponPolicyTagResponseDTO.builder()
                .couponPolicyTagId(1L)
                .couponPolicyId(1L)
                .tagId(1L)
                .build();
        List<CouponPolicyTagResponseDTO> couponPolicyTags = Collections.singletonList(couponPolicyTag);

        when(couponPolicyTagService.findAllCouponPolicyTags()).thenReturn(couponPolicyTags);

        mockMvc.perform(get("/coupon-policy-tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].couponPolicyTagId").value(1L))
                .andExpect(jsonPath("$[0].couponPolicyId").value(1L))
                .andExpect(jsonPath("$[0].tagId").value(1L));
    }

    @Test
    public void testFind() throws Exception {
        CouponPolicyTagResponseDTO couponPolicyTag = CouponPolicyTagResponseDTO.builder()
                .couponPolicyTagId(1L)
                .couponPolicyId(1L)
                .tagId(1L)
                .build();

        when(couponPolicyTagService.findCouponPolicyTagById(1L)).thenReturn(couponPolicyTag);

        mockMvc.perform(get("/coupon-policy-tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyTagId").value(1L))
                .andExpect(jsonPath("$.couponPolicyId").value(1L))
                .andExpect(jsonPath("$.tagId").value(1L));
    }

    @Test
    public void testCreate() throws Exception {
        CouponPolicyTagRequestDTO requestDTO = new CouponPolicyTagRequestDTO(1L, 1L);
        CouponPolicyTagResponseDTO responseDTO = CouponPolicyTagResponseDTO.builder()
                .couponPolicyTagId(1L)
                .couponPolicyId(1L)
                .tagId(1L)
                .build();

        when(couponPolicyTagService.createCouponPolicyTag(any(CouponPolicyTagRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupon-policy-tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponPolicyTagId").value(1L))
                .andExpect(jsonPath("$.couponPolicyId").value(1L))
                .andExpect(jsonPath("$.tagId").value(1L));
    }

    @Test
    public void testUpdate() throws Exception {
        CouponPolicyTagRequestDTO requestDTO = new CouponPolicyTagRequestDTO(1L, 2L);
        CouponPolicyTagResponseDTO responseDTO = CouponPolicyTagResponseDTO.builder()
                .couponPolicyTagId(1L)
                .couponPolicyId(1L)
                .tagId(2L)
                .build();

        when(couponPolicyTagService.updateCouponPolicyTag(eq(1L), any(CouponPolicyTagRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/coupon-policy-tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyTagId").value(1L))
                .andExpect(jsonPath("$.couponPolicyId").value(1L))
                .andExpect(jsonPath("$.tagId").value(2L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(couponPolicyTagService).deleteCouponPolicyTag(1L);

        mockMvc.perform(delete("/coupon-policy-tags/1"))
                .andExpect(status().isNoContent());

        verify(couponPolicyTagService, times(1)).deleteCouponPolicyTag(1L);
    }
}
