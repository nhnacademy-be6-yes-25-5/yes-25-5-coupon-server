package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link CouponPolicyService}의 구현 클래스입니다.
 * 이 클래스는 쿠폰 정책의 생성 및 조회 기능을 제공합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    /**
     * 모든 쿠폰 정책을 조회합니다.
     *
     * @return 모든 쿠폰 정책 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyResponseDTO> getAllCouponPolicies() {
        try {
            return couponPolicyRepository.findAll().stream()
                    .map(CouponPolicyResponseDTO::fromEntity)
                    .toList();
        } catch (DataAccessException e) {
            String errorMessage = "쿠폰 정책 조회 중 데이터베이스 오류가 발생했습니다.";
            log.error(errorMessage, e);
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus(errorMessage, 500, LocalDateTime.now())
            );
        } catch (Exception e) {
            String errorMessage = "쿠폰 정책 조회 중 예상치 못한 오류가 발생했습니다.";
            log.error(errorMessage, e);
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus(errorMessage, 500, LocalDateTime.now())
            );
        }
    }

    /**
     * 새로운 쿠폰 정책을 생성합니다.
     *
     * @param couponPolicyRequestDTO 쿠폰 정책 생성에 필요한 정보가 담긴 DTO
     * @return 생성된 쿠폰 정책 정보
     * @throws CouponPolicyServiceException 쿠폰 정책 생성 중 예외 발생 시
     */
    @Override
    public CouponPolicyResponseDTO createCouponPolicy(CouponPolicyRequestDTO couponPolicyRequestDTO) {
        try {
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(couponPolicyRequestDTO.couponPolicyName())
                    .couponPolicyDiscountValue(couponPolicyRequestDTO.couponPolicyDiscountValue())
                    .couponPolicyRate(couponPolicyRequestDTO.couponPolicyRate())
                    .couponPolicyMinOrderAmount(couponPolicyRequestDTO.couponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(couponPolicyRequestDTO.couponPolicyMaxAmount())
                    .couponPolicyDiscountType(couponPolicyRequestDTO.couponPolicyDiscountType())
                    .build();

            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            couponCreationUtil.createCoupon(savedCouponPolicy);

            return CouponPolicyResponseDTO.fromEntity(savedCouponPolicy);
        } catch (DataAccessException e) {
            String errorMessage = "쿠폰 정책 저장 중 데이터베이스 오류가 발생했습니다.";
            log.error(errorMessage, e);
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus(errorMessage, 500, LocalDateTime.now())
            );
        } catch (Exception e) {
            String errorMessage = "쿠폰 정책 생성 중 예상치 못한 오류가 발생했습니다.";
            log.error(errorMessage, e);
            throw new CouponPolicyServiceException(
                    ErrorStatus.toErrorStatus(errorMessage, 500, LocalDateTime.now())
            );
        }
    }

}