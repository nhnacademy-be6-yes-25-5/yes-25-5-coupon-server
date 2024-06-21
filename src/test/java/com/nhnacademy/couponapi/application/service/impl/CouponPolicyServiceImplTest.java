package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponPolicyServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponPolicyServiceImplTest {

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @InjectMocks
    private CouponPolicyServiceImpl couponPolicyService;

    private CouponPolicy couponPolicy;
    private CouponPolicyRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        couponPolicy = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                .couponPolicyRate(BigDecimal.valueOf(0.10))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                .couponPolicyDiscountType(true)
                .build();

        requestDTO = new CouponPolicyRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.10),
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true
        );
    }

    @Test
    public void testFindAllCouponPolicies() {
        when(couponPolicyRepository.findAll()).thenReturn(Collections.singletonList(couponPolicy));

        List<CouponPolicyResponseDTO> result = couponPolicyService.findAllCouponPolicies();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).couponPolicyId()).isEqualTo(1L);
    }

    @Test
    public void testFindCouponPolicyById() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.of(couponPolicy));

        CouponPolicyResponseDTO result = couponPolicyService.findCouponPolicyById(1L);

        assertThat(result.couponPolicyId()).isEqualTo(1L);
    }

    @Test
    public void testFindCouponPolicyById_NotFound() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CouponPolicyServiceException.class, () -> {
            couponPolicyService.findCouponPolicyById(1L);
        });
    }

    @Test
    public void testFindCouponPolicyEntityById() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.of(couponPolicy));

        CouponPolicy result = couponPolicyService.findCouponPolicyEntityById(1L);

        assertThat(result.getCouponPolicyId()).isEqualTo(1L);
    }

    @Test
    public void testFindCouponPolicyEntityById_NotFound() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CouponPolicyServiceException.class, () -> {
            couponPolicyService.findCouponPolicyEntityById(1L);
        });
    }

    @Test
    public void testCreateCouponPolicy() {
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);

        CouponPolicyResponseDTO result = couponPolicyService.createCouponPolicy(requestDTO);

        assertThat(result.couponPolicyId()).isEqualTo(1L);
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
    }

    @Test
    public void testUpdateCouponPolicy() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.of(couponPolicy));
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);

        CouponPolicyResponseDTO result = couponPolicyService.updateCouponPolicy(1L, requestDTO);

        assertThat(result.couponPolicyId()).isEqualTo(1L);
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
    }

    @Test
    public void testUpdateCouponPolicy_NotFound() {
        when(couponPolicyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CouponPolicyServiceException.class, () -> {
            couponPolicyService.updateCouponPolicy(1L, requestDTO);
        });
    }

    @Test
    public void testDeleteCouponPolicy() {
        doNothing().when(couponPolicyRepository).deleteById(1L);

        couponPolicyService.deleteCouponPolicy(1L);

        verify(couponPolicyRepository, times(1)).deleteById(1L);
    }

    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
