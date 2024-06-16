package com.nhnacademy.couponapi.service;

import com.nhnacademy.couponapi.model.*;
import com.nhnacademy.couponapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponPolicyRepository couponPolicyRepository;

    @Autowired
    private CouponUsageRepository couponUsageRepository;

    @Autowired
    private CouponPolicyTagRepository couponPolicyTagRepository;

    @Autowired
    private CouponTagCategoryRepository couponTagCategoryRepository;

    @Autowired
    private CouponPolicyBookRepository couponPolicyBookRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> getCouponById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
    }

    public CouponPolicy createCouponPolicy(CouponPolicy couponPolicy) {
        return couponPolicyRepository.save(couponPolicy);
    }

    public Optional<CouponPolicy> getCouponPolicyById(Long couponPolicyId) {
        return couponPolicyRepository.findById(couponPolicyId);
    }

    public List<CouponPolicy> getAllCouponPolicies() {
        return couponPolicyRepository.findAll();
    }

    public CouponUsage createCouponUsage(CouponUsage couponUsage) {
        return couponUsageRepository.save(couponUsage);
    }

    public List<CouponUsage> getAllCouponUsages() {
        return couponUsageRepository.findAll();
    }

    public CouponPolicyTag createCouponPolicyTag(CouponPolicyTag couponPolicyTag) {
        return couponPolicyTagRepository.save(couponPolicyTag);
    }

    public List<CouponPolicyTag> getAllCouponPolicyTags() {
        return couponPolicyTagRepository.findAll();
    }

    public CouponTagCategory createCouponTagCategory(CouponTagCategory couponTagCategory) {
        return couponTagCategoryRepository.save(couponTagCategory);
    }

    public List<CouponTagCategory> getAllCouponTagCategories() {
        return couponTagCategoryRepository.findAll();
    }

    public CouponPolicyBook createCouponPolicyBook(CouponPolicyBook couponPolicyBook) {
        return couponPolicyBookRepository.save(couponPolicyBook);
    }

    public List<CouponPolicyBook> getAllCouponPolicyBooks() {
        return couponPolicyBookRepository.findAll();
    }
}
