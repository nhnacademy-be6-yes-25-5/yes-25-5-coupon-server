//package com.nhnacademy.couponapi.repository;
//
//import com.nhnacademy.couponapi.model.CouponPolicy;
//import com.nhnacademy.couponapi.model.CouponPolicyBook;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
//public class CouponPolicyBookRepositoryTest {
//
//    @Autowired
//    private CouponPolicyBookRepository couponPolicyBookRepository;
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
//        couponPolicy = couponPolicyRepository.save(couponPolicy);
//
//        // Create and save a CouponPolicyBook
//        CouponPolicyBook couponPolicyBook = new CouponPolicyBook();
//        couponPolicyBook.setCouponPolicy(couponPolicy);
//        couponPolicyBook.setBookId(123L);
//        couponPolicyBook = couponPolicyBookRepository.save(couponPolicyBook);
//
//        // Retrieve the CouponPolicyBook by ID
//        Optional<CouponPolicyBook> foundCouponPolicyBook = couponPolicyBookRepository.findById(couponPolicyBook.getCouponPolicyBookId());
//
//        // Verify the results
//        assertTrue(foundCouponPolicyBook.isPresent());
//        assertEquals(couponPolicyBook.getCouponPolicyBookId(), foundCouponPolicyBook.get().getCouponPolicyBookId());
//        assertEquals(couponPolicyBook.getCouponPolicy().getCouponPolicyId(), foundCouponPolicyBook.get().getCouponPolicy().getCouponPolicyId());
//        assertEquals(couponPolicyBook.getBookId(), foundCouponPolicyBook.get().getBookId());
//    }
//}
