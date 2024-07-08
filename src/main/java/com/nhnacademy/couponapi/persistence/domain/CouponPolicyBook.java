package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_book")
public class CouponPolicyBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyBookId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Builder
    public CouponPolicyBook(Long couponPolicyBookId, CouponPolicy couponPolicy, Long bookId, String bookName) {
        this.couponPolicyBookId = couponPolicyBookId;
        this.couponPolicy = couponPolicy;
        this.bookId = bookId;
        this.bookName = bookName;
    }
}