//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.application.adapter.TagAdapter;
//import com.nhnacademy.couponapi.application.service.CouponPolicyService;
//import com.nhnacademy.couponapi.common.exception.CouponPolicyTagServiceException;
//import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
//import com.nhnacademy.couponapi.persistence.domain.CouponPolicyTag;
//import com.nhnacademy.couponapi.persistence.repository.CouponPolicyTagRepository;
//import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CouponPolicyTagServiceImplTest {
//
//    @Mock
//    private CouponPolicyTagRepository couponPolicyTagRepository;
//
//    @Mock
//    private CouponPolicyService couponPolicyService;
//
//    @Mock
//    private TagAdapter tagAdapter;
//
//    @InjectMocks
//    private CouponPolicyTagServiceImpl couponPolicyTagService;
//
//    private CouponPolicyTag couponPolicyTag;
//    private CouponPolicyTagRequestDTO requestDTO;
//    private CouponPolicy couponPolicy;
//
//    @BeforeEach
//    public void setUp() {
//        couponPolicy = CouponPolicy.builder()
//                .couponPolicyId(1L)
//                .couponPolicyName("Test Policy")
//                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
//                .couponPolicyRate(BigDecimal.valueOf(0.10))
//                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
//                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
//                .couponPolicyDiscountType(true)
//                .build();
//
//        couponPolicyTag = CouponPolicyTag.builder()
//                .couponPolicyTagId(1L)
//                .couponPolicy(couponPolicy)
//                .tagId(1L)
//                .build();
//
//        requestDTO = new CouponPolicyTagRequestDTO(1L, 1L);
//    }
//
//    @Test
//    public void testFindAllCouponPolicyTags() {
//        when(couponPolicyTagRepository.findAll()).thenReturn(Collections.singletonList(couponPolicyTag));
//
//        List<CouponPolicyTagResponseDTO> result = couponPolicyTagService.findAllCouponPolicyTags();
//
//        assertThat(result).hasSize(1);
//        assertThat(result.get(0).couponPolicyTagId()).isEqualTo(1L);
//    }
//
//    @Test
//    public void testFindCouponPolicyTagById() {
//        when(couponPolicyTagRepository.findById(1L)).thenReturn(Optional.of(couponPolicyTag));
//
//        CouponPolicyTagResponseDTO result = couponPolicyTagService.findCouponPolicyTagById(1L);
//
//        assertThat(result.couponPolicyTagId()).isEqualTo(1L);
//    }
//
//    @Test
//    public void testFindCouponPolicyTagById_NotFound() {
//        when(couponPolicyTagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(CouponPolicyTagServiceException.class, () -> {
//            couponPolicyTagService.findCouponPolicyTagById(1L);
//        });
//    }
//
//    @Test
//    public void testUpdateCouponPolicyTag_NotFound() {
//        when(couponPolicyTagRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(CouponPolicyTagServiceException.class, () -> {
//            couponPolicyTagService.updateCouponPolicyTag(1L, requestDTO);
//        });
//    }
//
//    @Test
//    public void testDeleteCouponPolicyTag() {
//        doNothing().when(couponPolicyTagRepository).deleteById(1L);
//
//        couponPolicyTagService.deleteCouponPolicyTag(1L);
//
//        verify(couponPolicyTagRepository, times(1)).deleteById(1L);
//    }
//}
