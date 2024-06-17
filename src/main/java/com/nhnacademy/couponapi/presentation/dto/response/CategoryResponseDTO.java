package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId;
}
