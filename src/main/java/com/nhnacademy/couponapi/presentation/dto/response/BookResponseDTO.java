package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;

import java.util.Date;

public record BookResponseDTO(
        Long bookId,
        String bookName,
        String bookIndex,
        String bookDescription,
        String bookAuthor,
        String bookPublisher,
        Date bookPublishDate,
        int bookPrice,
        int bookSellingPrice,
        String bookImage,
        int quantity,
        int reviewCount,
        int hitsCount,
        int searchCount,
        String bookIsbn
) {}
