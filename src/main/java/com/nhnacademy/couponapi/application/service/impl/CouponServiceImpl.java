package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    private final CouponPolicyService couponPolicyService;

    @Override
    public List<CouponResponseDTO> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponseDTO getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        return toResponseDTO(coupon);
    }

    @Override
    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {
        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponName(couponRequestDTO.getCouponName())
                .couponCode(couponRequestDTO.getCouponCode())
                .couponExpiredAt(couponRequestDTO.getCouponExpiredAt())
                .couponCreatedAt(couponRequestDTO.getCouponCreatedAt());

        if (couponRequestDTO.getCouponPolicyId() != null) {
            couponBuilder.couponPolicy(couponPolicyService.getCouponPolicyEntityById(couponRequestDTO.getCouponPolicyId()));
        }

        Coupon savedCoupon = couponRepository.save(couponBuilder.build());
        return toResponseDTO(savedCoupon);
    }

    @Override
    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponId(existingCoupon.getCouponId())
                .couponName(couponRequestDTO.getCouponName())
                .couponCode(couponRequestDTO.getCouponCode())
                .couponExpiredAt(couponRequestDTO.getCouponExpiredAt())
                .couponCreatedAt(couponRequestDTO.getCouponCreatedAt());

        if (couponRequestDTO.getCouponPolicyId() != null) {
            couponBuilder.couponPolicy(couponPolicyService.getCouponPolicyEntityById(couponRequestDTO.getCouponPolicyId()));
        }

        Coupon updatedCoupon = couponRepository.save(couponBuilder.build());
        return toResponseDTO(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Coupon getCouponEntityById(Long id) {  // 메서드 구현
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    private CouponResponseDTO toResponseDTO(Coupon coupon) {
        return CouponResponseDTO.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .couponCode(coupon.getCouponCode())
                .couponExpiredAt(coupon.getCouponExpiredAt())
                .couponCreatedAt(coupon.getCouponCreatedAt())
                .couponPolicyId(coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyId() : null)
                .build();
    }
}
