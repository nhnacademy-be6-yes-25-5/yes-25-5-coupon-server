package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponPolicyCategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponPolicyCategoryRepository couponPolicyCategoryRepository;

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
    void testFindByCategoryIdIn() {
        CouponPolicyCategory couponPolicyCategory1 = CouponPolicyCategory.builder()
                .couponPolicy(couponPolicy)
                .categoryId(1L)
                .categoryName("Category 1")
                .build();
        CouponPolicyCategory couponPolicyCategory2 = CouponPolicyCategory.builder()
                .couponPolicy(couponPolicy)
                .categoryId(2L)
                .categoryName("Category 2")
                .build();
        entityManager.persist(couponPolicyCategory1);
        entityManager.persist(couponPolicyCategory2);
        entityManager.flush();

        List<CouponPolicyCategory> foundCategories = couponPolicyCategoryRepository.findByCategoryIdIn(Arrays.asList(1L, 2L));

        assertNotNull(foundCategories);
        assertEquals(2, foundCategories.size());
        assertTrue(foundCategories.stream().anyMatch(category -> category.getCategoryId().equals(1L)));
        assertTrue(foundCategories.stream().anyMatch(category -> category.getCategoryId().equals(2L)));
    }

    @Test
    void testFindByCategoryIdIn_EmptyResult() {
        List<CouponPolicyCategory> foundCategories = couponPolicyCategoryRepository.findByCategoryIdIn(Arrays.asList(3L, 4L));

        assertNotNull(foundCategories);
        assertTrue(foundCategories.isEmpty());
    }
}