package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_policy_book")
@SuperBuilder(toBuilder = true)
public class CouponPolicyBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponPolicyBookId;

    @ManyToOne
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Builder
    public CouponPolicyBook(Long couponPolicyBookId, CouponPolicy couponPolicy, Long bookId) {
        this.couponPolicyBookId = couponPolicyBookId;
        this.couponPolicy = couponPolicy;
        this.bookId = bookId;
    }

    public CouponPolicyBook updateCouponPolicy(CouponPolicy couponPolicy) {
        return this.toBuilder()
                .couponPolicy(couponPolicy)
                .build();
    }

    public CouponPolicyBook updateBookId(Long bookId) {
        return this.toBuilder()
                .bookId(bookId)
                .build();
    }
}