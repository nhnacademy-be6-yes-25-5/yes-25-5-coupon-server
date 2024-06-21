package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
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
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CouponControllerTest {

    @Mock
    private CouponService couponService;

    @InjectMocks
    private CouponController couponController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        CouponUserListResponseDTO coupon = CouponUserListResponseDTO.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponCreatedAt(new Date())
                .couponExpiredAt(new Date())
                .build();
        List<CouponUserListResponseDTO> coupons = Collections.singletonList(coupon);

        when(couponService.findAllCoupons()).thenReturn(coupons);

        mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].couponId").value(1L));
    }

    @Test
    public void testFind() throws Exception {
        CouponResponseDTO coupon = CouponResponseDTO.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponCreatedAt(new Date())
                .couponExpiredAt(new Date())
                .build();

        when(couponService.findCouponById(1L)).thenReturn(coupon);

        mockMvc.perform(get("/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponId").value(1L));
    }

    @Test
    public void testCreate() throws Exception {
        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "Test Coupon",
                "TEST123",
                new Date(),
                1L
        );
        CouponResponseDTO responseDTO = CouponResponseDTO.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponCreatedAt(new Date())
                .couponExpiredAt(new Date())
                .build();

        when(couponService.createCoupon(any(CouponRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponId").value(1L));
    }

    @Test
    public void testUpdate() throws Exception {
        CouponRequestDTO requestDTO = new CouponRequestDTO(
                "Updated Coupon",
                "UPD123",
                new Date(),
                1L
        );
        CouponResponseDTO responseDTO = CouponResponseDTO.builder()
                .couponId(1L)
                .couponName("Updated Coupon")
                .couponCode("UPD123")
                .couponCreatedAt(new Date())
                .couponExpiredAt(new Date())
                .build();

        when(couponService.updateCoupon(eq(1L), any(CouponRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/coupons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponId").value(1L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(couponService).deleteCoupon(1L);

        mockMvc.perform(delete("/coupons/1"))
                .andExpect(status().isNoContent());

        verify(couponService, times(1)).deleteCoupon(1L);
    }
}
