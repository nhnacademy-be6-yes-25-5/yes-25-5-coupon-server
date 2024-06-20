package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyService couponPolicyService;

    @Override
    @Transactional(readOnly = true)
    public List<CouponUserListResponseDTO> findAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::toUserListResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponseDTO findCouponById(Long id) {

        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("Coupon not found by id", 404, LocalDateTime.now())
                ));
        return toResponseDTO(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Coupon findCouponEntityById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponServiceException(
                        ErrorStatus.toErrorStatus("Coupon not found by id", 404, LocalDateTime.now())
                ));
    }

    @Override
    public CouponResponseDTO createCoupon(CouponRequestDTO couponRequestDTO) {

        try {
            Coupon.CouponBuilder couponBuilder = Coupon.builder()
                    .couponName(couponRequestDTO.couponName())
                    .couponCode(couponRequestDTO.couponCode())
                    .couponExpiredAt(couponRequestDTO.couponExpiredAt());

            if (couponRequestDTO.couponPolicyId() != null) {
                couponBuilder.couponPolicy(couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
            }

            Coupon savedCoupon = couponRepository.save(couponBuilder.build());
            return toResponseDTO(savedCoupon);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be created", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO couponRequestDTO) {

        try {
            Coupon existingCoupon = couponRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            Coupon.CouponBuilder couponBuilder = Coupon.builder()
                    .couponId(existingCoupon.getCouponId())
                    .couponName(couponRequestDTO.couponName())
                    .couponCode(couponRequestDTO.couponCode())
                    .couponExpiredAt(couponRequestDTO.couponExpiredAt());

            if (couponRequestDTO.couponPolicyId() != null) {
                couponBuilder.couponPolicy(couponPolicyService.findCouponPolicyEntityById(couponRequestDTO.couponPolicyId()));
            }

            Coupon updatedCoupon = couponRepository.save(couponBuilder.build());
            return toResponseDTO(updatedCoupon);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be updated", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public void deleteCoupon(Long id) {

        try {
            couponRepository.deleteById(id);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("Coupon could not be deleted", 500, LocalDateTime.now())
            );
        }
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

    private CouponUserListResponseDTO toUserListResponseDTO(Coupon coupon) {
        return new CouponUserListResponseDTO(
                coupon.getCouponName(),
                coupon.getCouponCode(),
                coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyDiscountValue() : null,
                coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyRate() : null,
                coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyMinOrderAmount() : null,
                coupon.getCouponPolicy() != null ? coupon.getCouponPolicy().getCouponPolicyMaxAmount() : null,
                coupon.getCouponCreatedAt(),
                coupon.getCouponExpiredAt()
        );
    }
}
