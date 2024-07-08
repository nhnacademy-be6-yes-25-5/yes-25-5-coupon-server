package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
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

class CouponPolicyCategoryServiceImplTest {

    @Mock
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyCategoryServiceImpl couponPolicyCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCouponPolicyCategories() {
        CouponPolicy couponPolicy = createCouponPolicy();
        CouponPolicyCategory couponPolicyCategory = createCouponPolicyCategory(couponPolicy);

        when(couponPolicyCategoryRepository.findAll()).thenReturn(Collections.singletonList(couponPolicyCategory));

        List<CouponPolicyCategoryResponseDTO> response = couponPolicyCategoryService.getAllCouponPolicyCategories();

        assertEquals(1, response.size());
        verify(couponPolicyCategoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCouponPolicyCategory() {
        CouponPolicyCategoryRequestDTO requestDTO = createCouponPolicyCategoryRequestDTO();
        CouponPolicy couponPolicy = createCouponPolicy();
        CouponPolicyCategory couponPolicyCategory = createCouponPolicyCategory(couponPolicy);

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyCategoryRepository.save(any(CouponPolicyCategory.class))).thenReturn(couponPolicyCategory);

        CouponPolicyCategoryResponseDTO responseDTO = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(couponPolicyCategory.getCouponPolicyCategoryId(), responseDTO.couponPolicyCategoryId());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyCategoryRepository, times(1)).save(any(CouponPolicyCategory.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

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

    private CouponPolicyCategory createCouponPolicyCategory(CouponPolicy couponPolicy) {
        return CouponPolicyCategory.builder()
                .couponPolicyCategoryId(1L)
                .couponPolicy(couponPolicy)
                .categoryId(1L)
                .categoryName("Test Category")
                .build();
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
}