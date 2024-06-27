package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponPolicyBookControllerTest {

    @Mock
    private CouponPolicyBookService couponPolicyBookService;

    @InjectMocks
    private CouponPolicyBookController couponPolicyBookController;

    private CouponPolicyBookRequestDTO couponPolicyBookRequestDTO;
    private CouponPolicyBookResponseDTO couponPolicyBookResponseDTO;

    @BeforeEach
    void setUp() {
        couponPolicyBookRequestDTO = new CouponPolicyBookRequestDTO(1L, 1L);
        couponPolicyBookResponseDTO = new CouponPolicyBookResponseDTO(1L, 1L, 1L);
    }

    @Test
    public void testFindAll() {
        when(couponPolicyBookService.findAllCouponPolicyBooks()).thenReturn(List.of(couponPolicyBookResponseDTO));

        ResponseEntity<List<CouponPolicyBookResponseDTO>> response = couponPolicyBookController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).couponPolicyBookId()).isEqualTo(1L);

        verify(couponPolicyBookService, times(1)).findAllCouponPolicyBooks();
    }

    @Test
    public void testFind() {
        when(couponPolicyBookService.findCouponPolicyBookById(anyLong())).thenReturn(couponPolicyBookResponseDTO);

        ResponseEntity<CouponPolicyBookResponseDTO> response = couponPolicyBookController.find(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().couponPolicyBookId()).isEqualTo(1L);

        verify(couponPolicyBookService, times(1)).findCouponPolicyBookById(1L);
    }

    @Test
    public void testCreate() {
        when(couponPolicyBookService.createCouponPolicyBook(any(CouponPolicyBookRequestDTO.class))).thenReturn(couponPolicyBookResponseDTO);

        ResponseEntity<CouponPolicyBookResponseDTO> response = couponPolicyBookController.create(couponPolicyBookRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().couponPolicyBookId()).isEqualTo(1L);

        verify(couponPolicyBookService, times(1)).createCouponPolicyBook(couponPolicyBookRequestDTO);
    }

    @Test
    public void testUpdate() {
        when(couponPolicyBookService.updateCouponPolicyBook(anyLong(), any(CouponPolicyBookRequestDTO.class))).thenReturn(couponPolicyBookResponseDTO);

        ResponseEntity<CouponPolicyBookResponseDTO> response = couponPolicyBookController.update(1L, couponPolicyBookRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().couponPolicyBookId()).isEqualTo(1L);

        verify(couponPolicyBookService, times(1)).updateCouponPolicyBook(eq(1L), any(CouponPolicyBookRequestDTO.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(couponPolicyBookService).deleteCouponPolicyBook(anyLong());

        ResponseEntity<Void> response = couponPolicyBookController.delete(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(couponPolicyBookService, times(1)).deleteCouponPolicyBook(1L);
    }
}