package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponPolicyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponPolicyRepository couponPolicyRepository;

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
        entityManager.flush();
    }

    @Test
    void testSaveCouponPolicy() {
        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);
        assertNotNull(savedCouponPolicy);
        assertEquals(couponPolicy.getCouponPolicyName(), savedCouponPolicy.getCouponPolicyName());
    }

    @Test
    void testFindById() {
        Optional<CouponPolicy> foundCouponPolicy = couponPolicyRepository.findById(couponPolicy.getCouponPolicyId());
        assertTrue(foundCouponPolicy.isPresent());
        assertEquals(couponPolicy.getCouponPolicyName(), foundCouponPolicy.get().getCouponPolicyName());
    }

    @Test
    void testUpdateCouponPolicy() {
        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

        CouponPolicy updatedCouponPolicy = CouponPolicy.builder()
                .couponPolicyId(savedCouponPolicy.getCouponPolicyId())
                .couponPolicyName("Updated Policy")
                .couponPolicyDiscountValue(savedCouponPolicy.getCouponPolicyDiscountValue())
                .couponPolicyRate(savedCouponPolicy.getCouponPolicyRate())
                .couponPolicyMinOrderAmount(savedCouponPolicy.getCouponPolicyMinOrderAmount())
                .couponPolicyMaxAmount(savedCouponPolicy.getCouponPolicyMaxAmount())
                .couponPolicyDiscountType(savedCouponPolicy.isCouponPolicyDiscountType())
                .couponPolicyCreatedAt(savedCouponPolicy.getCouponPolicyCreatedAt())
                .couponPolicyUpdatedAt(savedCouponPolicy.getCouponPolicyUpdatedAt())
                .build();
        couponPolicyRepository.save(updatedCouponPolicy);

        Optional<CouponPolicy> foundUpdatedCouponPolicy = couponPolicyRepository.findById(savedCouponPolicy.getCouponPolicyId());
        assertTrue(foundUpdatedCouponPolicy.isPresent());
        assertEquals("Updated Policy", foundUpdatedCouponPolicy.get().getCouponPolicyName());
    }

    @Test
    void testDeleteCouponPolicy() {
        CouponPolicy savedCouponPolicy = couponPolicyRepository.save(couponPolicy);
        couponPolicyRepository.delete(savedCouponPolicy);

        Optional<CouponPolicy> deletedCouponPolicy = couponPolicyRepository.findById(savedCouponPolicy.getCouponPolicyId());
        assertFalse(deletedCouponPolicy.isPresent());
    }

    @Test
    void testFindAll() {
        CouponPolicy anotherCouponPolicy = CouponPolicy.builder()
                .couponPolicyName("Another Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(15.00))
                .couponPolicyRate(BigDecimal.valueOf(0.15))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(60.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(150.00))
                .couponPolicyDiscountType(false)
                .build();
        entityManager.persist(anotherCouponPolicy);
        entityManager.flush();

        Iterable<CouponPolicy> allCouponPolicies = couponPolicyRepository.findAll();
        assertNotNull(allCouponPolicies);
        assertTrue(allCouponPolicies.iterator().hasNext());
    }
}