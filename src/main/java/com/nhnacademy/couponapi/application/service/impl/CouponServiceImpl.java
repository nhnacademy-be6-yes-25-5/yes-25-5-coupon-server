package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponServiceException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link CouponService}의 구현 클래스입니다.
 * 이 클래스는 쿠폰의 생성 기능을 제공합니다.
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponPolicyBookRepository couponPolicyBookRepository;
    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;

    /**
     * 새로운 쿠폰을 생성합니다.
     *
     * @param coupon 생성할 쿠폰 정보
     * @return 생성된 쿠폰의 정보
     * @throws CouponServiceException 쿠폰 생성 중 예외 발생 시
     */
    @Override
    public CouponResponseDTO createCoupon(Coupon coupon) {
        try {
            Coupon savedCoupon = couponRepository.save(coupon);
            return CouponResponseDTO.fromEntity(savedCoupon);
        } catch (Exception e) {
            throw new CouponServiceException(
                    ErrorStatus.toErrorStatus("쿠폰을 생성할 수 없습니다", 500, LocalDateTime.now())
            );
        }
    }

    public List<Coupon> getCouponsByBookIdAndCategoryIds(Long bookId, List<Long> categoryIds) {
        List<CouponPolicyBook> bookPolicies = couponPolicyBookRepository.findByBookId(bookId);
        List<CouponPolicyCategory> categoryPolicies = couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds);

        List<CouponPolicy> couponPolicies = new ArrayList<>();
        for (CouponPolicyBook bookPolicy : bookPolicies) {
            couponPolicies.add(bookPolicy.getCouponPolicy());
        }
        for (CouponPolicyCategory categoryPolicy : categoryPolicies) {
            couponPolicies.add(categoryPolicy.getCouponPolicy());
        }

        return couponRepository.findByCouponPolicyIn(couponPolicies);
    }

}