package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponPolicyService couponPolicyService;

    @InjectMocks
    private CouponServiceImpl couponService;

    private Coupon coupon;
    private CouponPolicy couponPolicy;
    private CouponRequestDTO requestDTO;

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

        coupon = Coupon.builder()
                .couponId(1L)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponExpiredAt(new Date())
                .couponPolicy(couponPolicy)
                .build();

        requestDTO = new CouponRequestDTO("Test Coupon", "TEST123", new Date(), 1L);
    }

    @Test
    public void testFindAllCoupons() {
        when(couponRepository.findAll()).thenReturn(Collections.singletonList(coupon));

        List<CouponUserListResponseDTO> result = couponService.findAllCoupons();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).couponId()).isEqualTo(1L);
    }

    @Test
    public void testFindCouponById() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

        CouponResponseDTO result = couponService.findCouponById(1L);

        assertThat(result.couponId()).isEqualTo(1L);
    }

    @Test
    public void testFindCouponById_NotFound() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CouponServiceException.class, () -> {
            couponService.findCouponById(1L);
        });
    }

    @Test
    public void testCreateCoupon() {
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO result = couponService.createCoupon(requestDTO);

        assertThat(result.couponId()).isEqualTo(1L);
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testUpdateCoupon() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO result = couponService.updateCoupon(1L, requestDTO);

        assertThat(result.couponId()).isEqualTo(1L);
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testUpdateCoupon_NotFound() {
        when(couponRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CouponServiceException.class, () -> {
            couponService.updateCoupon(1L, requestDTO);
        });
    }

    @Test
    public void testDeleteCoupon() {
        doNothing().when(couponRepository).deleteById(1L);

        couponService.deleteCoupon(1L);

        verify(couponRepository, times(1)).deleteById(1L);
    }
}
