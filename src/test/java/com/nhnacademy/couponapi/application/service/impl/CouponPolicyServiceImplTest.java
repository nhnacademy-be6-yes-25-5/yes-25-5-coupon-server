package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponPolicyServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponPolicyServiceImplTest {

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyServiceImpl couponPolicyService;

    private CouponPolicyRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new CouponPolicyRequestDTO(
                "Test Policy",
                new BigDecimal("10.00"),
                new BigDecimal("0.10"),
                new BigDecimal("50.00"),
                new BigDecimal("100.00"),
                true
        );
    }

    @Test
    void getAllCouponPolicies() {
        CouponPolicy couponPolicy1 = CouponPolicy.builder().couponPolicyName("Test Policy 1").build();
        CouponPolicy couponPolicy2 = CouponPolicy.builder().couponPolicyName("Test Policy 2").build();

        when(couponPolicyRepository.findAll()).thenReturn(Arrays.asList(couponPolicy1, couponPolicy2));

        List<CouponPolicyResponseDTO> result = couponPolicyService.getAllCouponPolicies();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(couponPolicyRepository, times(1)).findAll();
    }

    @Test
    void createCouponPolicy_Success() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);

        CouponPolicyResponseDTO responseDTO = couponPolicyService.createCouponPolicy(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Test Policy", responseDTO.couponPolicyName());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

    @Test
    void createCouponPolicy_NullRequestDTO_ThrowsException() {
        CouponPolicyServiceException exception = assertThrows(CouponPolicyServiceException.class, () -> {
            couponPolicyService.createCouponPolicy(null);
        });

        assertEquals("요청 값이 비어있습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void createCouponPolicy_SaveReturnsNull_ThrowsException() {
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(null);

        CouponPolicyServiceException exception = assertThrows(CouponPolicyServiceException.class, () -> {
            couponPolicyService.createCouponPolicy(requestDTO);
        });

        assertEquals("쿠폰 정책 생성 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
    }
}