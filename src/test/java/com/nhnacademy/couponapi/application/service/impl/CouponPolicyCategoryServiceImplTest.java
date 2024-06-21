package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.CategoryAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyCategoryServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponPolicyCategoryServiceImplTest {

    @Mock
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    @Mock
    private CouponPolicyService couponPolicyService;

    @Mock
    private CategoryAdapter categoryAdapter;

    @InjectMocks
    private CouponPolicyCategoryServiceImpl couponPolicyCategoryService;

    private CouponPolicyCategory couponPolicyCategory;
    private CouponPolicyCategoryRequestDTO requestDTO;
    private CouponPolicy couponPolicy;

    @BeforeEach
    public void setUp() throws Exception {
        couponPolicy = mock(CouponPolicy.class);
        setPrivateField(couponPolicy, "couponPolicyId", 1L);

        couponPolicyCategory = mock(CouponPolicyCategory.class);
        setPrivateField(couponPolicyCategory, "couponPolicyCategoryId", 1L);
        when(couponPolicyCategory.getCouponPolicy()).thenReturn(couponPolicy);
        when(couponPolicyCategory.getCategoryId()).thenReturn(1L);

        requestDTO = new CouponPolicyCategoryRequestDTO(1L, 1L);
    }

    @Test
    public void testFindAllCouponPolicyCategories() {
        when(couponPolicyCategoryRepository.findAll()).thenReturn(Collections.singletonList(couponPolicyCategory));

        List<CouponPolicyCategoryResponseDTO> result = couponPolicyCategoryService.findAllCouponPolicyCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).couponPolicyCategoryId()).isEqualTo(0L);
    }

    @Test
    public void testFindCouponPolicyCategoryById() {
        when(couponPolicyCategoryRepository.findById(1L)).thenReturn(Optional.of(couponPolicyCategory));

        CouponPolicyCategoryResponseDTO result = couponPolicyCategoryService.findCouponPolicyCategoryById(1L);

        assertThat(result.couponPolicyCategoryId()).isEqualTo(0L);
    }

    @Test
    public void testCreateCouponPolicyCategory() {
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        doReturn(null).when(categoryAdapter).getCategoryById(1L);
        when(couponPolicyCategoryRepository.save(any(CouponPolicyCategory.class))).thenReturn(couponPolicyCategory);

        CouponPolicyCategoryResponseDTO result = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);

        assertThat(result.couponPolicyCategoryId()).isEqualTo(0L);
        verify(couponPolicyCategoryRepository, times(1)).save(any(CouponPolicyCategory.class));
    }

    @Test
    public void testUpdateCouponPolicyCategory() {
        when(couponPolicyCategoryRepository.findById(1L)).thenReturn(Optional.of(couponPolicyCategory));
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        doReturn(null).when(categoryAdapter).getCategoryById(1L);
        when(couponPolicyCategoryRepository.save(any(CouponPolicyCategory.class))).thenReturn(couponPolicyCategory);

        CouponPolicyCategoryResponseDTO result = couponPolicyCategoryService.updateCouponPolicyCategory(1L, requestDTO);

        assertThat(result.couponPolicyCategoryId()).isEqualTo(0L);
        verify(couponPolicyCategoryRepository, times(1)).save(couponPolicyCategory);
    }

    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
