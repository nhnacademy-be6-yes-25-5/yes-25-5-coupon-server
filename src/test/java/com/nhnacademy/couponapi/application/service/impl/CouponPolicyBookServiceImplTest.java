package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.adapter.BookAdapter;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.common.exception.CouponPolicyBookServiceException;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponPolicyBookServiceImplTest {

    @Mock
    private CouponPolicyBookRepository couponPolicyBookRepository;

    @Mock
    private CouponPolicyService couponPolicyService;

    @Mock
    private BookAdapter bookAdapter;

    @InjectMocks
    private CouponPolicyBookServiceImpl couponPolicyBookService;

    private CouponPolicyBook couponPolicyBook;
    private CouponPolicyBookRequestDTO requestDTO;
    private CouponPolicy couponPolicy;

    @BeforeEach
    public void setUp() throws Exception {
        couponPolicy = mock(CouponPolicy.class);
        setPrivateField(couponPolicy, "couponPolicyId", 1L);

        couponPolicyBook = mock(CouponPolicyBook.class);
        setPrivateField(couponPolicyBook, "couponPolicyBookId", 1L);
        when(couponPolicyBook.getCouponPolicy()).thenReturn(couponPolicy);
        when(couponPolicyBook.getBookId()).thenReturn(1L);

        requestDTO = new CouponPolicyBookRequestDTO(1L, 1L);
    }

    @Test
    public void testFindAllCouponPolicyBooks() throws Exception {
        when(couponPolicyBookRepository.findAll()).thenReturn(Collections.singletonList(couponPolicyBook));

        List<CouponPolicyBookResponseDTO> result = couponPolicyBookService.findAllCouponPolicyBooks();

        assertThat(result).hasSize(1);
        assertThat(getPrivateField(result.get(0), "couponPolicyBookId")).isEqualTo(0L);
    }

    @Test
    public void testFindCouponPolicyBookById() throws Exception {
        when(couponPolicyBookRepository.findById(1L)).thenReturn(Optional.of(couponPolicyBook));

        CouponPolicyBookResponseDTO result = couponPolicyBookService.findCouponPolicyBookById(1L);

        assertThat(getPrivateField(result, "couponPolicyBookId")).isEqualTo(0L);
    }

    @Test
    public void testCreateCouponPolicyBook() throws Exception {
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        doReturn(null).when(bookAdapter).getBookById(1L);
        when(couponPolicyBookRepository.save(any(CouponPolicyBook.class))).thenReturn(couponPolicyBook);

        CouponPolicyBookResponseDTO result = couponPolicyBookService.createCouponPolicyBook(requestDTO);

        assertThat(getPrivateField(result, "couponPolicyBookId")).isEqualTo(0L);
        verify(couponPolicyBookRepository, times(1)).save(any(CouponPolicyBook.class));
    }

    @Test
    public void testUpdateCouponPolicyBook() throws Exception {
        when(couponPolicyBookRepository.findById(1L)).thenReturn(Optional.of(couponPolicyBook));
        when(couponPolicyService.findCouponPolicyEntityById(1L)).thenReturn(couponPolicy);
        doReturn(null).when(bookAdapter).getBookById(1L);
        when(couponPolicyBookRepository.save(any(CouponPolicyBook.class))).thenReturn(couponPolicyBook);

        CouponPolicyBookResponseDTO result = couponPolicyBookService.updateCouponPolicyBook(1L, requestDTO);

        assertThat(getPrivateField(result, "couponPolicyBookId")).isEqualTo(0L);
        verify(couponPolicyBookRepository, times(1)).save(couponPolicyBook);
    }

    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    private Object getPrivateField(Object targetObject, String fieldName) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(targetObject);
    }
}
