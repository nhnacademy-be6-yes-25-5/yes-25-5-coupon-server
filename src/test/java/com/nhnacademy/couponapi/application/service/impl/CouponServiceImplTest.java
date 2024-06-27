package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.UserCoupon;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.persistence.repository.UserCouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.ReadOrderUserCouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponPolicyService couponPolicyService;

    @Mock
    private UserCouponRepository userCouponRepository;

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
    public void testCreateCoupon_Exception() {
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenThrow(new RuntimeException("Service exception"));

        assertThrows(CouponServiceException.class, () -> {
            couponService.createCoupon(requestDTO);
        });
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
    public void testUpdateCoupon_Exception() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenThrow(new RuntimeException("Service exception"));

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

    @Test
    public void testDeleteCoupon_Exception() {
        doThrow(new RuntimeException("Repository exception")).when(couponRepository).deleteById(1L);

        assertThrows(CouponServiceException.class, () -> {
            couponService.deleteCoupon(1L);
        });
    }

    @Test
    public void testIssueBirthdayCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO result = couponService.issueBirthdayCoupon(1L);

        assertThat(result.couponName()).isEqualTo("Test Coupon");
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testIssueWelcomeCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponseDTO result = couponService.issueWelcomeCoupon(1L);

        assertThat(result.couponName()).isEqualTo("Test Coupon");
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    public void testFindBestCoupon() {
        Long userId = 1L;
        BigDecimal orderAmount = new BigDecimal("50000");

        CouponPolicy policy1 = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Policy 1")
                .couponPolicyDiscountValue(new BigDecimal("5000"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("20000"))
                .couponPolicyMaxAmount(new BigDecimal("10000"))
                .couponPolicyDiscountType(true)
                .build();

        CouponPolicy policy2 = CouponPolicy.builder()
                .couponPolicyId(2L)
                .couponPolicyName("Policy 2")
                .couponPolicyDiscountValue(new BigDecimal("10000"))
                .couponPolicyRate(new BigDecimal("0.20"))
                .couponPolicyMinOrderAmount(new BigDecimal("30000"))
                .couponPolicyMaxAmount(new BigDecimal("15000"))
                .couponPolicyDiscountType(false)
                .build();

        Coupon coupon1 = Coupon.builder()
                .couponId(1L)
                .couponName("Coupon 1")
                .couponCode("CODE1")
                .couponExpiredAt(new Date())
                .couponPolicy(policy1)
                .build();

        Coupon coupon2 = Coupon.builder()
                .couponId(2L)
                .couponName("Coupon 2")
                .couponCode("CODE2")
                .couponExpiredAt(new Date())
                .couponPolicy(policy2)
                .build();

        UserCoupon userCoupon1 = UserCoupon.builder()
                .userCouponId(1L)
                .userId(userId)
                .coupon(coupon1)
                .couponStatus(UserCoupon.CouponStatus.ACTIVE)
                .build();

        UserCoupon userCoupon2 = UserCoupon.builder()
                .userCouponId(2L)
                .userId(userId)
                .coupon(coupon2)
                .couponStatus(UserCoupon.CouponStatus.ACTIVE)
                .build();

        List<UserCoupon> userCoupons = Arrays.asList(userCoupon1, userCoupon2);

        when(userCouponRepository.findByUserId(userId)).thenReturn(userCoupons);

        ReadOrderUserCouponResponse response = couponService.findBestCoupon(userId, orderAmount);

        assertEquals(coupon2.getCouponId(), response.couponId());
        assertEquals(new BigDecimal("10000"), response.discountAmount());

        verify(userCouponRepository, times(1)).findByUserId(userId);
    }

    @Test //할인금액이 같을때 만료일자가 더 가까운 쿠폰을 반환하는 테스트
    void testFindBestCoupon_SameDiscountAmount() {
        Long userId = 1L;
        BigDecimal orderAmount = new BigDecimal("50000");

        CouponPolicy policy1 = CouponPolicy.builder()
                .couponPolicyId(1L)
                .couponPolicyName("Policy 1")
                .couponPolicyDiscountValue(new BigDecimal("5000"))
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("20000"))
                .couponPolicyMaxAmount(new BigDecimal("10000"))
                .couponPolicyDiscountType(true)
                .build();

        CouponPolicy policy2 = CouponPolicy.builder()
                .couponPolicyId(2L)
                .couponPolicyName("Policy 2")
                .couponPolicyDiscountValue(new BigDecimal("5000")) // 동일한 할인 금액
                .couponPolicyRate(new BigDecimal("0.10"))
                .couponPolicyMinOrderAmount(new BigDecimal("20000"))
                .couponPolicyMaxAmount(new BigDecimal("10000"))
                .couponPolicyDiscountType(true)
                .build();

        Date closerExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24); // 1일 후 만료
        Date fartherExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48); // 2일 후 만료

        Coupon coupon1 = Coupon.builder()
                .couponId(1L)
                .couponName("Coupon 1")
                .couponCode("CODE1")
                .couponExpiredAt(closerExpirationDate)
                .couponPolicy(policy1)
                .build();

        Coupon coupon2 = Coupon.builder()
                .couponId(2L)
                .couponName("Coupon 2")
                .couponCode("CODE2")
                .couponExpiredAt(fartherExpirationDate)
                .couponPolicy(policy2)
                .build();

        UserCoupon userCoupon1 = UserCoupon.builder()
                .userCouponId(1L)
                .userId(userId)
                .coupon(coupon1)
                .couponStatus(UserCoupon.CouponStatus.ACTIVE)
                .build();

        UserCoupon userCoupon2 = UserCoupon.builder()
                .userCouponId(2L)
                .userId(userId)
                .coupon(coupon2)
                .couponStatus(UserCoupon.CouponStatus.ACTIVE)
                .build();

        List<UserCoupon> userCoupons = Arrays.asList(userCoupon1, userCoupon2);

        when(userCouponRepository.findByUserId(userId)).thenReturn(userCoupons);

        ReadOrderUserCouponResponse response = couponService.findBestCoupon(userId, orderAmount);

        assertEquals(coupon1.getCouponId(), response.couponId()); // 만료일자가 더 가까운 쿠폰 선택
        assertEquals(new BigDecimal("5000.00"), response.discountAmount());

        verify(userCouponRepository, times(1)).findByUserId(userId);
    }
}