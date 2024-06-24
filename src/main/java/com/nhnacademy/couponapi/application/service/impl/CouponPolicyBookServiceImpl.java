package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.BookAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
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
    private final BookAdapter bookAdapter;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyBookResponseDTO> findAllCouponPolicyBooks() {
        return couponPolicyBookRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicyBookResponseDTO findCouponPolicyBookById(Long id) {

        CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
                .orElseThrow(() -> new CouponPolicyBookServiceException(
                        ErrorStatus.toErrorStatus("Coupon policy book not found", 404, LocalDateTime.now())
                ));

        return toResponseDTO(couponPolicyBook);
    }

    @Override
    public CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO) {

        try {
            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
            bookAdapter.findByBookId(requestDTO.bookId());
            CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                    .couponPolicy(couponPolicy)
                    .bookId(requestDTO.bookId())
                    .build();
            CouponPolicyBook savedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);

            return toResponseDTO(savedCouponPolicyBook);
        } catch (Exception e) {
            throw new CouponPolicyBookServiceException(
                    ErrorStatus.toErrorStatus("Error creating CouponPolicyBook", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public CouponPolicyBookResponseDTO updateCouponPolicyBook(Long id, CouponPolicyBookRequestDTO requestDTO) {

        try {
            CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
                    .orElseThrow(() -> new CouponPolicyBookServiceException(
                            ErrorStatus.toErrorStatus("Coupon policy book not found by id", 404, LocalDateTime.now())));
            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
            bookAdapter.findByBookId(requestDTO.bookId());
            couponPolicyBook.updateCouponPolicy(couponPolicy);
            couponPolicyBook.updateBookId(requestDTO.bookId());
            CouponPolicyBook updatedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);

            return toResponseDTO(updatedCouponPolicyBook);
        } catch (Exception e) {
            throw new CouponPolicyBookServiceException(
                    ErrorStatus.toErrorStatus("Error updating CouponPolicyBook", 500, LocalDateTime.now())
            );
        }
    }

    @Override
    public void deleteCouponPolicyBook(Long id) {

        try {
            couponPolicyBookRepository.deleteById(id);
        } catch (Exception e) {
            throw new CouponPolicyBookServiceException(
                    ErrorStatus.toErrorStatus("Error deleting CouponPolicyBook", 500, LocalDateTime.now())
            );
        }
    }

    private CouponPolicyBookResponseDTO toResponseDTO(CouponPolicyBook couponPolicyBook) {
        return CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(couponPolicyBook.getCouponPolicyBookId())
                .couponPolicyId(couponPolicyBook.getCouponPolicy().getCouponPolicyId())
                .bookId(couponPolicyBook.getBookId())
                .build();
    }

}
