package com.nhnacademy.couponapi.persistence.repository;

import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponRepository couponRepository;

    private CouponPolicy couponPolicy;
    private Coupon coupon;

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

        coupon = Coupon.builder()
                .couponPolicy(couponPolicy)
                .couponName("Test Coupon")
                .couponCode("TEST123")
                .couponExpiredAt(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .build();
        entityManager.persist(coupon);
        entityManager.flush();
    }

    @Test
    void testSaveCoupon() {
        Coupon savedCoupon = couponRepository.save(coupon);
        assertNotNull(savedCoupon);
        assertEquals(coupon.getCouponName(), savedCoupon.getCouponName());
    }

    @Test
    void testFindById() {
        Optional<Coupon> foundCoupon = couponRepository.findById(coupon.getCouponId());
        assertTrue(foundCoupon.isPresent());
        assertEquals(coupon.getCouponName(), foundCoupon.get().getCouponName());
    }

    @Test
    void testFindByCouponPolicyIn() {
        CouponPolicy anotherCouponPolicy = CouponPolicy.builder()
                .couponPolicyName("Another Policy")
                .couponPolicyDiscountValue(BigDecimal.valueOf(15.00))
                .couponPolicyRate(BigDecimal.valueOf(0.15))
                .couponPolicyMinOrderAmount(BigDecimal.valueOf(60.00))
                .couponPolicyMaxAmount(BigDecimal.valueOf(150.00))
                .couponPolicyDiscountType(false)
                .build();
        entityManager.persist(anotherCouponPolicy);

        List<Coupon> foundCoupons = couponRepository.findByCouponPolicyIn(List.of(couponPolicy, anotherCouponPolicy));

        assertNotNull(foundCoupons);
        assertEquals(1, foundCoupons.size());
        assertEquals(coupon.getCouponName(), foundCoupons.get(0).getCouponName());
    }

    @Test
    void testDeleteByCouponExpiredAtBefore() {
        Date now = new Date(System.currentTimeMillis() + 40L * 24 * 60 * 60 * 1000); // Future date to ensure coupon is deleted
        couponRepository.deleteByCouponExpiredAtBefore(now);

        Optional<Coupon> deletedCoupon = couponRepository.findById(coupon.getCouponId());
        assertFalse(deletedCoupon.isPresent());
    }

    @Test
    void testDeleteCoupon() {
        Coupon savedCoupon = couponRepository.save(coupon);
        couponRepository.delete(savedCoupon);

        Optional<Coupon> deletedCoupon = couponRepository.findById(savedCoupon.getCouponId());
        assertFalse(deletedCoupon.isPresent());
    }

    @Test
    void testFindAll() {
        Coupon anotherCoupon = Coupon.builder()
                .couponPolicy(couponPolicy)
                .couponName("Another Coupon")
                .couponCode("ANOTHER123")
                .couponExpiredAt(new Date(System.currentTimeMillis() + 60L * 24 * 60 * 60 * 1000))
                .couponCreatedAt(new Date())
                .build();
        entityManager.persist(anotherCoupon);
        entityManager.flush();

        Iterable<Coupon> allCoupons = couponRepository.findAll();
        assertNotNull(allCoupons);
        assertTrue(allCoupons.iterator().hasNext());
    }
}