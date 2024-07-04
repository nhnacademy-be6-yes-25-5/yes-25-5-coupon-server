package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link CouponService}의 구현 클래스입니다.
 * 이 클래스는 쿠폰의 생성 및 조회 기능을 제공합니다.
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

    /**
     * 도서 ID와 카테고리 ID 목록에 해당하는 쿠폰들을 조회합니다.
     *
     * @param bookId      도서 ID
     * @param categoryIds 카테고리 ID 목록
     * @return 조회된 쿠폰 목록
     */
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

    /**
     * 쿠폰의 만료 날짜를 조회합니다.
     *
     * @param couponId 쿠폰 ID
     * @return 쿠폰의 만료 날짜
     * @throws CouponNotFoundException 주어진 ID에 해당하는 쿠폰이 없는 경우
     */
    public Date getCouponExpiredDate(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found with id: " + couponId));
        return coupon.getCouponExpiredAt();
    }

    /**
     * 만료된 쿠폰들을 삭제합니다. 이 메서드는 매일 자정에 실행됩니다.
     */
    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredCoupons() {
        Date now = new Date();
        couponRepository.deleteByCouponExpiredAtBefore(now);
    }

    @Override
    public List<Coupon> getCouponsInfo(List<Long> couponIdList) {
        return couponRepository.findAllById(couponIdList);
    }

}