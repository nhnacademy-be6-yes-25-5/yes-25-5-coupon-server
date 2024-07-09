package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponPolicyBookServiceImplTest {

    @Mock
    private CouponPolicyBookRepository couponPolicyBookRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyBookServiceImpl couponPolicyBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCouponPolicyBooks() {
        CouponPolicy couponPolicy = createCouponPolicy();
        CouponPolicyBook couponPolicyBook = createCouponPolicyBook(couponPolicy);

        when(couponPolicyBookRepository.findAll()).thenReturn(Collections.singletonList(couponPolicyBook));

        List<CouponPolicyBookResponseDTO> response = couponPolicyBookService.getAllCouponPolicyBooks();

        assertEquals(1, response.size());
        verify(couponPolicyBookRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCouponPolicyBooksWithDatabaseException() {
        when(couponPolicyBookRepository.findAll()).thenThrow(new DataAccessException("DB error") {});

        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.getAllCouponPolicyBooks();
        });

        assertEquals("도서 쿠폰 정책 조회 실패", exception.getErrorStatus().getMessage());
        verify(couponPolicyBookRepository, times(1)).findAll();
    }

    @Test
    void testCreateCouponPolicyBook() {
        CouponPolicyBookRequestDTO requestDTO = createCouponPolicyBookRequestDTO();
        CouponPolicy couponPolicy = createCouponPolicy();
        CouponPolicyBook couponPolicyBook = createCouponPolicyBook(couponPolicy);

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyBookRepository.save(any(CouponPolicyBook.class))).thenReturn(couponPolicyBook);

        CouponPolicyBookResponseDTO responseDTO = couponPolicyBookService.createCouponPolicyBook(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(couponPolicyBook.getCouponPolicyBookId(), responseDTO.couponPolicyBookId());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyBookRepository, times(1)).save(any(CouponPolicyBook.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

    @Test
    void testCreateCouponPolicyBookWithDatabaseException() {
        CouponPolicyBookRequestDTO requestDTO = createCouponPolicyBookRequestDTO();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenThrow(new DataAccessException("DB error") {});

        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.createCouponPolicyBook(requestDTO);
        });

        assertEquals("도서 쿠폰 정책 저장 실패 - 데이터베이스 오류", exception.getErrorStatus().getMessage());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyBookRepository, times(0)).save(any(CouponPolicyBook.class));
    }

    @Test
    void testCreateCouponPolicyBookWithUnknownException() {
        CouponPolicyBookRequestDTO requestDTO = createCouponPolicyBookRequestDTO();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenThrow(new RuntimeException("Unknown error"));

        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.createCouponPolicyBook(requestDTO);
        });

        assertEquals("도서 쿠폰 정책 저장 실패 - 알 수 없는 오류", exception.getErrorStatus().getMessage());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyBookRepository, times(0)).save(any(CouponPolicyBook.class));
    }

    private CouponPolicy createCouponPolicy() {
        return CouponPolicy.builder()
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                .couponPolicyRate(BigDecimal.valueOf(0.10))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                .couponPolicyDiscountType(true)
                .build();
    }

    private CouponPolicyBook createCouponPolicyBook(CouponPolicy couponPolicy) {
        return CouponPolicyBook.builder()
                .couponPolicyBookId(1L)
                .couponPolicy(couponPolicy)
                .bookId(1L)
                .bookName("Test Book")
                .build();
    }

    private CouponPolicyBookRequestDTO createCouponPolicyBookRequestDTO() {
        return new CouponPolicyBookRequestDTO(
                "Test Policy",
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.10),
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00),
                true,
                "Test Book",
                1L
        );
    }
}