package com.nhnacademy.couponapi.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
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
public class CouponPolicyBookControllerTest {

    @Mock
    private CouponPolicyBookService couponPolicyBookService;

    @InjectMocks
    private CouponPolicyBookController couponPolicyBookController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(couponPolicyBookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        CouponPolicyBookResponseDTO couponPolicyBook = CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(1L)
                .couponPolicyId(1L)
                .bookId(1L)
                .build();
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = Collections.singletonList(couponPolicyBook);

        when(couponPolicyBookService.findAllCouponPolicyBooks()).thenReturn(couponPolicyBooks);

        mockMvc.perform(get("/coupon-policy-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].couponPolicyBookId").value(1L));
    }

    @Test
    public void testFind() throws Exception {
        CouponPolicyBookResponseDTO couponPolicyBook = CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(1L)
                .couponPolicyId(1L)
                .bookId(1L)
                .build();

        when(couponPolicyBookService.findCouponPolicyBookById(1L)).thenReturn(couponPolicyBook);

        mockMvc.perform(get("/coupon-policy-books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyBookId").value(1L));
    }

    @Test
    public void testCreate() throws Exception {
        CouponPolicyBookRequestDTO requestDTO = new CouponPolicyBookRequestDTO(
                1L,
                1L
        );
        CouponPolicyBookResponseDTO responseDTO = CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(1L)
                .couponPolicyId(1L)
                .bookId(1L)
                .build();

        when(couponPolicyBookService.createCouponPolicyBook(any(CouponPolicyBookRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/coupon-policy-books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponPolicyBookId").value(1L));
    }

    @Test
    public void testUpdate() throws Exception {
        CouponPolicyBookRequestDTO requestDTO = new CouponPolicyBookRequestDTO(
                1L,
                1L
        );
        CouponPolicyBookResponseDTO responseDTO = CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(1L)
                .couponPolicyId(1L)
                .bookId(1L)
                .build();

        when(couponPolicyBookService.updateCouponPolicyBook(eq(1L), any(CouponPolicyBookRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/coupon-policy-books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponPolicyBookId").value(1L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(couponPolicyBookService).deleteCouponPolicyBook(1L);

        mockMvc.perform(delete("/coupon-policy-books/1"))
                .andExpect(status().isNoContent());

        verify(couponPolicyBookService, times(1)).deleteCouponPolicyBook(1L);
    }
}
