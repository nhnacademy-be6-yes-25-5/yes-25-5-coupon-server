package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class CouponPolicyBookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponPolicyBookRepository couponPolicyBookRepository;

    private CouponPolicy couponPolicy;

    @BeforeEach
    void setUp() {
        couponPolicy = CouponPolicy.builder()
                .couponPolicyName("Test Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(10.00))
                .couponPolicyRate(BigDecimal.valueOf(0.10))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(50.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(100.00))
                .couponPolicyDiscountType(true)
                .build();
        entityManager.persist(couponPolicy);
    }

    @Test
    void testFindByBookId() {
        CouponPolicyBook couponPolicyBook = CouponPolicyBook.builder()
                .couponPolicy(couponPolicy)
                .bookId(1L)
                .bookName("Test Book")
                .build();
        entityManager.persist(couponPolicyBook);
        entityManager.flush();

        List<CouponPolicyBook> found = couponPolicyBookRepository.findByBookId(1L);

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals("Test Book", found.get(0).getBookName());
    }
}