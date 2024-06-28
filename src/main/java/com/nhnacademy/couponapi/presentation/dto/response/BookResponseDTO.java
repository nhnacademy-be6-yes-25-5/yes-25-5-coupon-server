package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.util.Date;

public record BookResponseDTO(
        Long bookId,
        String bookName,
        String authorName,
        String bookPublisher
) {}
