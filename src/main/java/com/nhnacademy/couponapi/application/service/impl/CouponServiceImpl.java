package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.exception.CouponCreationException;
import com.nhnacademy.couponapi.common.exception.CouponNotFoundException;
import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyBook;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyBookRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
import com.nhnacademy.couponapi.persistence.repository.CouponRepository;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link CouponService}의 구현 클래스입니다.
 * 이 클래스는 쿠폰의 생성 및 조회 기능을 제공합니다.
 */
@Slf4j
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
     */
    @Override
    public CouponResponseDTO createCoupon(Coupon coupon) {
        if (coupon == null) {
            throw new CouponCreationException(ErrorStatus.toErrorStatus("쿠폰 정보가 비어있습니다.", 400, LocalDateTime.now()));
        }

        Coupon savedCoupon = couponRepository.save(coupon);

        return CouponResponseDTO.fromEntity(savedCoupon);
    }

    /**
     * 도서 ID와 카테고리 ID 목록에 해당하는 쿠폰들을 조회합니다.
     *
     * @param bookId      도서 ID
     * @param categoryIds 카테고리 ID 목록
     * @return 조회된 쿠폰 목록
     */
    @Override
    public List<Coupon> getAllByBookIdAndCategoryIds(Long bookId, List<Long> categoryIds) {
        if (bookId == null || categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("도서 ID와 카테고리 ID 목록은 비어있을 수 없습니다.");
        }

        List<CouponPolicyBook> bookPolicies = couponPolicyBookRepository.findByBookId(bookId);
        List<CouponPolicyCategory> categoryPolicies = couponPolicyCategoryRepository.findByCategoryIdIn(categoryIds);

        List<CouponPolicy> couponPolicies = new ArrayList<>();
        couponPolicies.addAll(bookPolicies.stream()
                .map(CouponPolicyBook::getCouponPolicy)
                .toList());
        couponPolicies.addAll(categoryPolicies.stream()
                .map(CouponPolicyCategory::getCouponPolicy)
                .toList());

        return couponRepository.findByCouponPolicyIn(couponPolicies);
    }

    /**
     * 쿠폰의 만료 날짜를 조회합니다.
     *
     * @param couponId 쿠폰 ID
     * @return 쿠폰의 만료 날짜
     * @throws CouponNotFoundException 주어진 ID에 해당하는 쿠폰이 없는 경우
     */
    @Override
    public Date getCouponExpiredDate(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(ErrorStatus.toErrorStatus("해당 ID의 쿠폰을 찾을 수 없습니다: " + couponId, 404, LocalDateTime.now())));

        return coupon.getCouponExpiredAt();
    }

    /**
     * 만료된 쿠폰들을 삭제합니다. 이 메서드는 매일 자정에 실행됩니다.
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeExpiredCoupons() {
        Date now = new Date();
        couponRepository.deleteByCouponExpiredAtBefore(now);
    }

    /**
     * 쿠폰 ID 목록에 해당하는 쿠폰들을 조회합니다.
     *
     * @param couponIdList 쿠폰 ID 목록
     * @return 조회된 쿠폰 목록
     */
    @Override
    public List<CouponInfoResponse> getAllByCouponIdList(List<Long> couponIdList) {
        if (couponIdList == null || couponIdList.isEmpty()) {
            throw new IllegalArgumentException("쿠폰 ID 목록은 비어있을 수 없습니다.");
        }

        List<Coupon> coupons = couponRepository.findAllById(couponIdList);

        return coupons.stream()
                .map(coupon -> {
                    Long bookId = coupon.getCouponPolicy().getCouponPolicyBooks().stream()
                            .findFirst()
                            .map(CouponPolicyBook::getBookId)
                            .orElse(null);
                    List<Long> categoryIds = coupon.getCouponPolicy().getCouponPolicyCategories().stream()
                            .map(CouponPolicyCategory::getCategoryId)
                            .collect(Collectors.toList());
                    Boolean applyCouponToAllBooks = coupon.getCouponPolicy().getCouponPolicyBooks() == null || coupon.getCouponPolicy().getCouponPolicyBooks().isEmpty();
                    return CouponInfoResponse.builder()
                            .couponId(coupon.getCouponId())
                            .couponName(coupon.getCouponName())
                            .couponMinAmount(coupon.getCouponPolicy().getCouponPolicyMinOrderAmount())
                            .couponMaxAmount(coupon.getCouponPolicy().getCouponPolicyMaxAmount())
                            .couponDiscountAmount(coupon.getCouponPolicy().getCouponPolicyDiscountValue())
                            .couponDiscountRate(coupon.getCouponPolicy().getCouponPolicyRate())
                            .couponCreatedAt(coupon.getCouponCreatedAt())
                            .couponCode(coupon.getCouponCode())
                            .bookId(bookId)
                            .categoryIds(categoryIds)
                            .couponDiscountType(coupon.getCouponPolicy().isCouponPolicyDiscountType())
                            .applyCouponToAllBooks(applyCouponToAllBooks)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
