package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponPolicyServiceImplTest {

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyServiceImpl couponPolicyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCouponPolicies() {
        CouponPolicy couponPolicy = createCouponPolicy();

        when(couponPolicyRepository.findAll()).thenReturn(Collections.singletonList(couponPolicy));

        List<CouponPolicyResponseDTO> response = couponPolicyService.findAllCouponPolicies();

        assertEquals(1, response.size());
        verify(couponPolicyRepository, times(1)).findAll();
    }

    @Test
    void testCreateCouponPolicy() {
        CouponPolicyRequestDTO requestDTO = createCouponPolicyRequestDTO();
        CouponPolicy couponPolicy = createCouponPolicy();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);

        CouponPolicyResponseDTO responseDTO = couponPolicyService.createCouponPolicy(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(couponPolicy.getCouponPolicyId(), responseDTO.couponPolicyId());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

//    @Test
//    void testCreateCouponPolicy_Exception() {
//        CouponPolicyRequestDTO requestDTO = createCouponPolicyRequestDTO();
//        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenThrow(new RuntimeException("Error"));
//
//        Exception exception = assertThrows(CouponPolicyServiceException.class, () ->
//                couponPolicyService.createCouponPolicy(requestDTO));
//
//        assertEquals("쿠폰 정책 생성 실패", exception.getMessage());
//        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
//        verify(couponCreationUtil, never()).createCoupon(any(CouponPolicy.class));
//    }

    private CouponPolicy createCouponPolicy() {
        return CouponPolicy.builder()
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                .couponPolicyRate(BigDecimal.valueOf(0.10))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                .couponPolicyDiscountType(true)
                .build();
    }

    private CouponPolicyRequestDTO createCouponPolicyRequestDTO() {
        return new CouponPolicyRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.10),
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true
        );
    }
}