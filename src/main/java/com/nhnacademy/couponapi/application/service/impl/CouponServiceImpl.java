package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyService couponPolicyService;

    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponseDTO getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        return toResponseDTO(coupon);
    }

    @Override
    @Transactional
    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {
        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponName(couponRequestDTO.couponName())
                .couponCode(couponRequestDTO.couponCode())
                .couponExpiredAt(couponRequestDTO.couponExpiredAt())
                .couponCreatedAt(couponRequestDTO.couponCreatedAt());

        if (couponRequestDTO.couponPolicyId() != null) {
            couponBuilder.couponPolicy(couponPolicyService.getCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
        }

        Coupon savedCoupon = couponRepository.save(couponBuilder.build());
        return toResponseDTO(savedCoupon);
    }

    @Override
    @Transactional
    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        Coupon.CouponBuilder couponBuilder = Coupon.builder()
                .couponId(existingCoupon.getCouponId())
                .couponName(couponRequestDTO.couponName())
                .couponCode(couponRequestDTO.couponCode())
                .couponExpiredAt(couponRequestDTO.couponExpiredAt())
                .couponCreatedAt(couponRequestDTO.couponCreatedAt());

        if (couponRequestDTO.couponPolicyId() != null) {
            couponBuilder.couponPolicy(couponPolicyService.getCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
        }

        Coupon updatedCoupon = couponRepository.save(couponBuilder.build());
        return toResponseDTO(updatedCoupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon getCouponEntityById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    private CouponResponseDTO toResponseDTO(Coupon coupon) {
        return new CouponResponseDTO(
                coupon.getCouponId(),
                coupon.getCouponName(),
                coupon.getCouponCode(),
                coupon.getCouponExpiredAt(),
                coupon.getCouponCreatedAt(),
                coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyId() : null
        );
    }
}
