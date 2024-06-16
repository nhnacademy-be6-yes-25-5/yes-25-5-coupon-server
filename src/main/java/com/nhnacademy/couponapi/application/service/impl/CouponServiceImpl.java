//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.persistance.domain.Coupon;
//import com.nhnacademy.couponapi.persistance.repository.CouponRepository;
//import com.nhnacademy.couponapi.presentation.dto.request.CreateCouponRequest;
//import com.nhnacademy.couponapi.presentation.dto.request.UseCouponRequest;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponResponse;
//import com.nhnacademy.couponapi.application.service.CouponService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CouponServiceImpl implements CouponService {
//
//    @Autowired
//    private CouponRepository couponRepository;
//
//    @Override
//    public CouponResponse createCoupon(CreateCouponRequest request) {
//        // Coupon creation logic
//        Coupon coupon = new Coupon();
//        // set coupon properties from request
//        couponRepository.save(coupon);
//        return new CouponResponse(coupon);
//    }
//
//    @Override
//    public List<CouponResponse> getCouponsByUser(Long userId) {
//        // Fetch coupons by userId
//        return couponRepository.findByUserId(userId).stream()
//                .map(CouponResponse::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void useCoupon(UseCouponRequest request) {
//        // Coupon usage logic
//        Coupon coupon = couponRepository.findById(request.getCouponId()).orElseThrow();
//        // update coupon usage
//        couponRepository.save(coupon);
//    }
//}
