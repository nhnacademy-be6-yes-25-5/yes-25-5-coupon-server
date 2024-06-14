//package com.nhnacademy.couponapi.repository;
//
//import com.nhnacademy.couponapi.model.CouponPolicy;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ActiveProfiles("test")
//public class CouponPolicyRepositoryTest {
//
//    @Autowired
//    private CouponPolicyRepository couponPolicyRepository;
//
//    @Test
//    public void testSaveAndFindById() {
//        // Create and save a CouponPolicy
//        CouponPolicy couponPolicy = new CouponPolicy();
//        couponPolicy.setCouponPolicyName("Test Policy");
//        couponPolicy.setCouponPolicyDiscountType(true);
//        couponPolicy.setCouponPolicyDiscountValue(100L);
//        couponPolicy.setCouponPolicyMaxAmount(500.0);
//        couponPolicy.setCouponPolicyMinOrderAmount(50.0);
//        couponPolicy.setCouponPolicyRate(10);
//        couponPolicy = couponPolicyRepository.save(couponPolicy);
//
//        // Retrieve the CouponPolicy by ID
//        Optional<CouponPolicy> foundCouponPolicy = couponPolicyRepository.findById(couponPolicy.getCouponPolicyId());
//
//        // Verify the results
//        assertTrue(foundCouponPolicy.isPresent());
//        assertEquals(couponPolicy.getCouponPolicyId(), foundCouponPolicy.get().getCouponPolicyId());
//        assertEquals(couponPolicy.getCouponPolicyName(), foundCouponPolicy.get().getCouponPolicyName());
//        assertEquals(couponPolicy.getCouponPolicyDiscountType(), foundCouponPolicy.get().getCouponPolicyDiscountType());
//        assertEquals(couponPolicy.getCouponPolicyDiscountValue(), foundCouponPolicy.get().getCouponPolicyDiscountValue());
//        assertEquals(couponPolicy.getCouponPolicyMaxAmount(), foundCouponPolicy.get().getCouponPolicyMaxAmount());
//        assertEquals(couponPolicy.getCouponPolicyMinOrderAmount(), foundCouponPolicy.get().getCouponPolicyMinOrderAmount());
//        assertEquals(couponPolicy.getCouponPolicyRate(), foundCouponPolicy.get().getCouponPolicyRate());
//    }
//}
