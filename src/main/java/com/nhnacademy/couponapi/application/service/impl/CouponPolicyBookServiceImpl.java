package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.BookAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponPolicyBookServiceImpl implements CouponPolicyBookService {

    private final CouponPolicyBookRepository couponPolicyBookRepository;

    private final CouponPolicyService couponPolicyService;

    private final BookAdapter bookAdapter;

    @Override
    public List<CouponPolicyBookResponseDTO> getAllCouponPolicyBooks() {
        return couponPolicyBookRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CouponPolicyBookResponseDTO getCouponPolicyBookById(Long id) {

        CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyBook not found"));

        return toResponseDTO(couponPolicyBook);
    }

    @Override
    public CouponPolicyBookResponseDTO createCouponPolicyBook(CouponPolicyBookRequestDTO requestDTO) {

        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.getCouponPolicyId());
        bookAdapter.getBookById(requestDTO.getBookId());
        CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                .couponPolicy(couponPolicy)
                .bookId(requestDTO.getBookId())
                .build();
        CouponPolicyBook savedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);

        return toResponseDTO(savedCouponPolicyBook);
    }

    @Override
    public CouponPolicyBookResponseDTO updateCouponPolicyBook(Long id, CouponPolicyBookRequestDTO requestDTO) {

        CouponPolicyBook couponPolicyBook = couponPolicyBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyBook not found"));
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.getCouponPolicyId());
        bookAdapter.getBookById(requestDTO.getBookId());
        couponPolicyBook.setCouponPolicy(couponPolicy);
        couponPolicyBook.setBookId(requestDTO.getBookId());
        CouponPolicyBook updatedCouponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);

        return toResponseDTO(updatedCouponPolicyBook);
    }

    @Override
    public void deleteCouponPolicyBook(Long id) {
        couponPolicyBookRepository.deleteById(id);
    }

    private CouponPolicyBookResponseDTO toResponseDTO(CouponPolicyBook couponPolicyBook) {
        return CouponPolicyBookResponseDTO.builder()
                .couponPolicyBookId(couponPolicyBook.getCouponPolicyBookId())
                .couponPolicyId(couponPolicyBook.getCouponPolicy().getCouponPolicyId())
                .bookId(couponPolicyBook.getBookId())
                .build();
    }

}
