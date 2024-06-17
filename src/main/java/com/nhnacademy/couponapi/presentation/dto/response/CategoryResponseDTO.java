package com.nhnacademy.couponapi.presentation.dto.response;

public record CategoryResponseDTO(
        Long categoryId,
        String categoryName,
        Long parentCategoryId
) {}
