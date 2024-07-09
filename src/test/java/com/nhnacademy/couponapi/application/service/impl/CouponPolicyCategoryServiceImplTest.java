package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponPolicyCategoryServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
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
class CouponPolicyCategoryServiceImplTest {

    @Mock
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyCategoryServiceImpl couponPolicyCategoryService;

    private CouponPolicyCategoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new CouponPolicyCategoryRequestDTO(
                "Test Policy",
                new BigDecimal("10.00"),
                new BigDecimal("0.10"),
                new BigDecimal("50.00"),
                new BigDecimal("100.00"),
                true,
                "Test Category",
                1L
        );
    }

    @Test
    void getAllCouponPolicyCategories() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();
        CouponPolicyCategory couponPolicyCategory1 = CouponPolicyCategory.builder().couponPolicyCategoryId(1L).couponPolicy(couponPolicy).categoryId(1L).categoryName("Test Category 1").build();
        CouponPolicyCategory couponPolicyCategory2 = CouponPolicyCategory.builder().couponPolicyCategoryId(2L).couponPolicy(couponPolicy).categoryId(2L).categoryName("Test Category 2").build();

        when(couponPolicyCategoryRepository.findAll()).thenReturn(Arrays.asList(couponPolicyCategory1, couponPolicyCategory2));

        List<CouponPolicyCategoryResponseDTO> result = couponPolicyCategoryService.getAllCouponPolicyCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(couponPolicyCategoryRepository, times(1)).findAll();
    }

    @Test
    void createCouponPolicyCategory_Success() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();
        CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder().couponPolicyCategoryId(1L).couponPolicy(couponPolicy).categoryId(1L).categoryName("Test Category").build();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyCategoryRepository.save(any(CouponPolicyCategory.class))).thenReturn(couponPolicyCategory);

        CouponPolicyCategoryResponseDTO responseDTO = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.couponPolicyCategoryId());
        assertEquals("Test Category", responseDTO.categoryName());
        assertEquals(1L, responseDTO.categoryId());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyCategoryRepository, times(1)).save(any(CouponPolicyCategory.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

    @Test
    void createCouponPolicyCategory_NullRequestDTO_ThrowsException() {
        CouponPolicyCategoryServiceException exception = assertThrows(CouponPolicyCategoryServiceException.class, () -> {
            couponPolicyCategoryService.createCouponPolicyCategory(null);
        });

        assertEquals("요청 값이 비어있습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void createCouponPolicyCategory_CouponPolicyRepositorySaveReturnsNull_ThrowsException() {
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(null);

        CouponPolicyCategoryServiceException exception = assertThrows(CouponPolicyCategoryServiceException.class, () -> {
            couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
        });

        assertEquals("쿠폰 정책 생성 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void createCouponPolicyCategory_CouponPolicyCategoryRepositorySaveReturnsNull_ThrowsException() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyCategoryRepository.save(any(CouponPolicyCategory.class))).thenReturn(null);

        CouponPolicyCategoryServiceException exception = assertThrows(CouponPolicyCategoryServiceException.class, () -> {
            couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
        });

        assertEquals("카테고리 쿠폰 정책 생성 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
    }
}