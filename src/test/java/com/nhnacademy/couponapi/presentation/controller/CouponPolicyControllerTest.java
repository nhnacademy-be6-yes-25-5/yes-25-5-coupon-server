package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponPolicyControllerTest {

    @Mock
    private CouponPolicyService couponPolicyService;

    @InjectMocks
    private CouponPolicyController couponPolicyController;

    private CouponPolicyRequestDTO couponPolicyRequestDTO;
    private CouponPolicyResponseDTO couponPolicyResponseDTO;

    @BeforeEach
    void setUp() {
        couponPolicyRequestDTO = new CouponPolicyRequestDTO(
                "Test Policy",
                new BigDecimal("10.00"),
                null,
                new BigDecimal("50.00"),
                new BigDecimal("100.00"),
                true
        );

        couponPolicyResponseDTO = CouponPolicyResponseDTO.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(new BigDecimal("10.00"))
                .couponPolicyCreatedAt(new Date())
                .couponPolicyUpdatedAt(new Date())
                .couponPolicyRate(null)
                .couponPolicyMinOrderAmount(new BigDecimal("50.00"))
                .couponPolicyMaxAmount(new BigDecimal("100.00"))
                .couponPolicyDiscountType(true)
                .build();
    }

    @Test
    public void testFindAll() {
        when(couponPolicyService.findAllCouponPolicies()).thenReturn(List.of(couponPolicyResponseDTO));

        ResponseEntity<List<CouponPolicyResponseDTO>> response = couponPolicyController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).couponPolicyId()).isEqualTo(1L);

        verify(couponPolicyService, times(1)).findAllCouponPolicies();
    }

    @Test
    public void testCreate() {
        when(couponPolicyService.createCouponPolicy(any(CouponPolicyRequestDTO.class))).thenReturn(couponPolicyResponseDTO);

        ResponseEntity<CouponPolicyResponseDTO> response = couponPolicyController.create(couponPolicyRequestDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().couponPolicyId()).isEqualTo(1L);

        verify(couponPolicyService, times(1)).createCouponPolicy(couponPolicyRequestDTO);
    }

}