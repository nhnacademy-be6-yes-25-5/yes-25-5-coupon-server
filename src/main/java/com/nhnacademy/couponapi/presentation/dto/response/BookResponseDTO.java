package com.nhnacademy.couponapi.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class BookResponseDTO {
    private Long bookId;
    private String bookName;
    private String bookIndex;
    private String bookDescription;
    private String bookAuthor;
    private String bookPublisher;
    private Date bookPublishDate;
    private int bookPrice;
    private int bookSellingPrice;
    private String bookImage;
    private int quantity;
    private int reviewCount;
    private int hitsCount;
    private int searchCount;
    private String bookIsbn;
}
