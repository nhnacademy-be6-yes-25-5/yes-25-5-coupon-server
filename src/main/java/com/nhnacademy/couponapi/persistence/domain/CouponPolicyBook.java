package com.nhnacademy.couponapi.persistence.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public CouponPolicyBook(Long couponPolicyBookId, CouponPolicy couponPolicy, Long bookId) {
        this.couponPolicyBookId = couponPolicyBookId;
        this.couponPolicy = couponPolicy;
        this.bookId = bookId;
    }

    public void setCouponPolicy(CouponPolicy couponPolicy) {
        this.couponPolicy = couponPolicy;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
