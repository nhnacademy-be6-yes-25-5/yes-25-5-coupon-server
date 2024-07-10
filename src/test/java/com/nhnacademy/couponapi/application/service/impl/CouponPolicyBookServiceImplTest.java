package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponPolicyBookServiceImplTest {

    @Mock
    private CouponPolicyBookRepository couponPolicyBookRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponCreationUtil couponCreationUtil;

    @InjectMocks
    private CouponPolicyBookServiceImpl couponPolicyBookService;

    private CouponPolicyBookRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new CouponPolicyBookRequestDTO(
                "Test Policy",
                new BigDecimal("10.00"),
                new BigDecimal("0.10"),
                new BigDecimal("50.00"),
                new BigDecimal("100.00"),
                true,
                "Test Book",
                1L
        );
    }

    @Test
    void getAllCouponPolicyBooks() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();
        CouponPolicyBook couponPolicyBook1 = CouponPolicyBook.builder().couponPolicyBookId(1L).couponPolicy(couponPolicy).bookId(1L).bookName("Test Book 1").build();
        CouponPolicyBook couponPolicyBook2 = CouponPolicyBook.builder().couponPolicyBookId(2L).couponPolicy(couponPolicy).bookId(2L).bookName("Test Book 2").build();

        when(couponPolicyBookRepository.findAll()).thenReturn(Arrays.asList(couponPolicyBook1, couponPolicyBook2));

        List<CouponPolicyBookResponseDTO> result = couponPolicyBookService.getAllCouponPolicyBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(couponPolicyBookRepository, times(1)).findAll();
    }

    @Test
    void createCouponPolicyBook_Success() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();
        CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder().couponPolicyBookId(1L).couponPolicy(couponPolicy).bookId(1L).bookName("Test Book").build();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyBookRepository.save(any(CouponPolicyBook.class))).thenReturn(couponPolicyBook);

        CouponPolicyBookResponseDTO responseDTO = couponPolicyBookService.createCouponPolicyBook(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.couponPolicyBookId());
        assertEquals("Test Book", responseDTO.bookName());
        assertEquals(1L, responseDTO.bookId());
        verify(couponPolicyRepository, times(1)).save(any(CouponPolicy.class));
        verify(couponPolicyBookRepository, times(1)).save(any(CouponPolicyBook.class));
        verify(couponCreationUtil, times(1)).createCoupon(any(CouponPolicy.class));
    }

    @Test
    void createCouponPolicyBook_NullRequestDTO_ThrowsException() {
        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.createCouponPolicyBook(null);
        });

        assertEquals("요청 값이 비어있습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void createCouponPolicyBook_CouponPolicySaveError_ThrowsException() {
        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(null);

        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.createCouponPolicyBook(requestDTO);
        });

        assertEquals("쿠폰 정책을 저장하는 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
    }

    @Test
    void createCouponPolicyBook_CouponPolicyBookSaveError_ThrowsException() {
        CouponPolicy couponPolicy = CouponPolicy.builder().couponPolicyName("Test Policy").build();

        when(couponPolicyRepository.save(any(CouponPolicy.class))).thenReturn(couponPolicy);
        when(couponPolicyBookRepository.save(any(CouponPolicyBook.class))).thenReturn(null);

        CouponPolicyBookServiceException exception = assertThrows(CouponPolicyBookServiceException.class, () -> {
            couponPolicyBookService.createCouponPolicyBook(requestDTO);
        });

        assertEquals("도서 쿠폰 정책을 저장하는 중 오류가 발생했습니다.", exception.getErrorStatus().getMessage());
    }
}