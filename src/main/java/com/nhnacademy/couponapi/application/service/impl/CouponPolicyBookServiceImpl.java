package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
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
    private final CouponPolicyService couponPolicyService;
    private final CouponPolicyRepository couponPolicyRepository;

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

//    @Override
//    @Transactional(readOnly = true)
//    public CouponPolicyResponseDTO findCouponPolicyBookById(Long id) {
//
//        CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
//                .orElseThrow(() -> new CouponPolicyBookServiceException(
//                        ErrorStatus.toErrorStatus("Coupon policy book not found", 404, LocalDateTime.now())
//                ));
//        String bookName = bookAdapter.findByBookId(couponPolicyBook.getBookId()).getBookName();
//        return CouponPolicyResponseDTO.fromEntity(couponPolicyBook.getCouponPolicy(), bookName, null);
//    }
//
//    @Override
//    public CouponPolicyResponseDTO updateCouponPolicyBook(Long id, CouponPolicyBookRequestDTO requestDTO) {
//
//        try {
//            CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
//                    .orElseThrow(() -> new CouponPolicyBookServiceException(
//                            ErrorStatus.toErrorStatus("Coupon policy book not found by id", 404, LocalDateTime.now())));
//            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
//            Long bookId = bookAdapter.findByBookName(requestDTO.bookName()).getBookId();
//            couponPolicyBook.updateCouponPolicy(couponPolicy);
//            couponPolicyBook.updateBookId(bookId);
//            CouponPolicyBook updatedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);
//
//            return CouponPolicyResponseDTO.fromEntity(updatedCouponPolicyBook.getCouponPolicy(), requestDTO.bookName(), null);
//        } catch (Exception e) {
//            throw new CouponPolicyBookServiceException(
//                    ErrorStatus.toErrorStatus("Error updating CouponPolicyBook", 500, LocalDateTime.now())
//            );
//        }
//    }
//
//    @Override
//    public void deleteCouponPolicyBook(Long id) {
//
//        try {
//            couponPolicyBookRepository.deleteById(id);
//        } catch (Exception e) {
//            throw new CouponPolicyBookServiceException(
//                    ErrorStatus.toErrorStatus("Error deleting CouponPolicyBook", 500, LocalDateTime.now())
//            );
//        }
//    }
}