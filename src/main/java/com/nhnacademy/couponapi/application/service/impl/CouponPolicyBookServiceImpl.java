package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link CouponPolicyBookService}의 구현 클래스입니다.
 * 이 클래스는 도서에 대한 쿠폰 정책의 생성 및 조회 기능을 제공합니다.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyBookServiceImpl implements CouponPolicyBookService {
    private static final Logger log = LoggerFactory.getLogger(CouponPolicyBookServiceImpl.class);
    private final CouponPolicyBookRepository couponPolicyBookRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    /**
     * 도서에 대한 모든 쿠폰 정책을 조회합니다.
     *
     * @return 도서에 대한 모든 쿠폰 정책 목록
     */
    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyBookResponseDTO> getAllCouponPolicyBooks() {
        return couponPolicyBookRepository.findAll().stream()
                .map(CouponPolicyBookResponseDTO::fromEntity)
                .toList();
    }

    /**
     * 도서에 대한 새로운 쿠폰 정책을 생성합니다.
     *
     * @param requestDTO 도서에 대한 쿠폰 정책 생성에 필요한 정보가 담긴 DTO
     * @return 도서에 대해 생성된 쿠폰 정책 정보
     * @throws CouponPolicyBookServiceException 도서에 대한 쿠폰 정책 생성 중 예외 발생 시
     */
    @Transactional
    public CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO) {
        try {
            // 쿠폰 정책 생성
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(requestDTO.couponPolicyName())
                    .couponPolicyDiscountValue(requestDTO.couponPolicyDiscountValue())
                    .couponPolicyRate(requestDTO.couponPolicyRate())
                    .couponPolicyMinOrderAmount(requestDTO.couponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(requestDTO.couponPolicyMaxAmount())
                    .couponPolicyDiscountType(requestDTO.couponPolicyDiscountType())
                    .build();
            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            // 도서 쿠폰 정책 생성
            CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                    .couponPolicy(savedCouponPolicy)
                    .bookId(requestDTO.bookId())
                    .bookName(requestDTO.bookName())
                    .build();
            CouponPolicyBook savedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);

            // 쿠폰 생성
            couponCreationUtil.createCoupon(savedCouponPolicy);

            return CouponPolicyBookResponseDTO.builder()
                    .couponPolicyBookId(savedCouponPolicyBook.getCouponPolicyBookId())
                    .bookId(savedCouponPolicyBook.getBookId())
                    .bookName(savedCouponPolicyBook.getBookName())
                    .couponPolicy(CouponPolicyResponseDTO.fromEntity(savedCouponPolicyBook.getCouponPolicy()))
                    .build();

        } catch (Exception e) {
            String errorMessage = "도서 쿠폰 정책 저장 실패";
            log.error(errorMessage, e);
            throw new CouponPolicyBookServiceException(
                    ErrorStatus.toErrorStatus(errorMessage, 500, LocalDateTime.now())
            );
        }
    }
}