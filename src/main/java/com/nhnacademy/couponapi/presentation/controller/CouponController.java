package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.persistence.domain.Coupon;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import com.nhnacademy.couponapi.presentation.dto.response.ExpiredCouponUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 쿠폰 컨트롤러 클래스입니다.
 * 쿠폰 관련 API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 API")
public class CouponController {

    private final CouponService couponService;

    /**
     * 도서 ID와 카테고리 ID 목록에 해당하는 쿠폰들을 조회합니다.
     *
     * @param bookId      도서 ID
     * @param categoryIds 카테고리 ID 목록
     * @return 조회된 쿠폰 목록
     */
    @GetMapping
    @Operation(summary = "도서 ID와 카테고리 ID 목록에 해당하는 쿠폰 조회", description = "도서 ID와 카테고리 ID 목록에 해당하는 쿠폰들을 조회합니다.")
    public List<BookDetailCouponResponseDTO> getCoupons(
            @Parameter(description = "도서 ID", required = true) @RequestParam Long bookId,
            @Parameter(description = "카테고리 ID 목록", required = true) @RequestParam List<Long> categoryIds) {
        List<Coupon> coupons = couponService.getCouponsByBookIdAndCategoryIds(bookId, categoryIds);
        return coupons.stream()
                .map(coupon -> BookDetailCouponResponseDTO.builder()
                        .couponId(coupon.getCouponId())
                        .couponName(coupon.getCouponName())
                        .couponExpiredAt(coupon.getCouponExpiredAt())
                        .couponPolicyName(coupon.getCouponPolicy().getCouponPolicyName())
                        .couponPolicyDiscountValue(coupon.getCouponPolicy().getCouponPolicyDiscountValue())
                        .couponPolicyRate(coupon.getCouponPolicy().getCouponPolicyRate())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 쿠폰의 만료 날짜를 조회합니다.
     *
     * @param couponId 쿠폰 ID
     * @return 만료 날짜를 포함한 응답 객체
     */
    @PostMapping("/expired")
    @Operation(summary = "쿠폰 만료 날짜 조회", description = "쿠폰의 만료 날짜를 조회합니다.")
    public ExpiredCouponUserResponse getCouponExpiredDate(@Parameter(description = "쿠폰 ID", required = true) @RequestParam Long couponId) {
        Date couponExpiredAt = couponService.getCouponExpiredDate(couponId);
        return new ExpiredCouponUserResponse(couponExpiredAt);
    }

    @GetMapping("/info")
    public List<CouponInfoResponse> getCouponsInfo(@RequestParam List<Long> couponIdList) {
        return couponService.getCouponsInfo(couponIdList)
                .stream()
                .map(coupon -> CouponInfoResponse.builder()
                        .couponId(coupon.getCouponId())
                        .couponName(coupon.getCouponName())
                        .couponMinAmount(coupon.getCouponPolicy().getCouponPolicyMinOrderAmount())
                        .couponMaxAmount(coupon.getCouponPolicy().getCouponPolicyMaxAmount())
                        .couponDiscountAmount(coupon.getCouponPolicy().getCouponPolicyDiscountValue())
                        .couponDiscountRate(coupon.getCouponPolicy().getCouponPolicyRate())
                        .couponCreatedAt(coupon.getCouponCreatedAt())
                        .couponCode(coupon.getCouponCode())
                        .couponDiscountType(coupon.getCouponPolicy().isCouponPolicyDiscountType())
                        .build())
                .collect(Collectors.toList());
    }

}