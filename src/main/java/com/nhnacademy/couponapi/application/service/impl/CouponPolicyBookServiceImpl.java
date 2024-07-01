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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponPolicyBookServiceImpl implements CouponPolicyBookService {
    private final CouponPolicyBookRepository couponPolicyBookRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponCreationUtil couponCreationUtil;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyBookResponseDTO> findAllCouponPolicyBooks() {
        return couponPolicyBookRepository.findAll().stream()
                .map(CouponPolicyBookResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO) {
        try {
            // 쿠폰 정책 생성
            CouponPolicy couponPolicy = CouponPolicy.builder()
                    .couponPolicyName(requestDTO.getCouponPolicyName())
                    .couponPolicyDiscountValue(requestDTO.getCouponPolicyDiscountValue())
                    .couponPolicyRate(requestDTO.getCouponPolicyRate())
                    .couponPolicyMinOrderAmount(requestDTO.getCouponPolicyMinOrderAmount())
                    .couponPolicyMaxAmount(requestDTO.getCouponPolicyMaxAmount())
                    .couponPolicyDiscountType(requestDTO.isCouponPolicyDiscountType())
                    .build();
            CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

            // 도서 쿠폰 정책 생성
            CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                    .couponPolicy(savedCouponPolicy)
                    .bookId(requestDTO.getBookId())
                    .bookName(requestDTO.getBookName())
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
            String errorMessage = "An unexpected error occurred while creating CouponPolicyBook.";
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new CouponPolicyBookServiceException(
                    ErrorStatus.toErrorStatus("Book coupon policy does not saved", 500, LocalDateTime.now())
            );
        }
    }
}