package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.common.jwt.JwtUserDetails;
import com.nhnacademy.couponapi.common.jwt.annotation.CurrentUser;
import com.nhnacademy.couponapi.presentation.dto.response.BookDetailCouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponInfoResponse;
import com.nhnacademy.couponapi.presentation.dto.response.ExpiredCouponUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(
            summary = "도서 ID와 카테고리 ID 목록에 해당하는 쿠폰 조회",
            description = "도서 ID와 카테고리 ID 목록에 해당하는 쿠폰들을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 쿠폰 조회",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookDetailCouponResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public ResponseEntity<List<BookDetailCouponResponseDTO>> getCoupons(
            @Parameter(description = "도서 ID", required = true) @RequestParam Long bookId,
            @Parameter(description = "카테고리 ID 목록", required = true) @RequestParam List<Long> categoryIds,
            @CurrentUser JwtUserDetails jwtUserDetails) {
        if (Objects.isNull(jwtUserDetails)) {
            return ResponseEntity.ok(couponService.getAllByBookIdAndCategoryIds(bookId, categoryIds));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUserDetails.accessToken())
                .header("Refresh-Token", jwtUserDetails.refreshToken())
                .body(couponService.getAllByBookIdAndCategoryIds(bookId, categoryIds));
    }

    /**
     * 쿠폰의 만료 날짜를 조회합니다.
     *
     * @param couponId 쿠폰 ID
     * @return 만료 날짜를 포함한 응답 객체
     */
    @PostMapping("/expired")
    @Operation(
            summary = "쿠폰 만료 날짜 조회",
            description = "쿠폰의 만료 날짜를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 만료 날짜 조회",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExpiredCouponUserResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public ExpiredCouponUserResponse getCouponExpiredDate(@Parameter(description = "쿠폰 ID", required = true) @RequestParam Long couponId) {
        Date couponExpiredAt = couponService.getCouponExpiredDate(couponId);
        return new ExpiredCouponUserResponse(couponExpiredAt);
    }

    /**
     * 쿠폰 정보들을 조회합니다.
     *
     * @param couponIdList 쿠폰 ID 목록
     * @return 쿠폰 정보 목록
     */
    @GetMapping("/info")
    @Operation(
            summary = "쿠폰 정보 조회",
            description = "쿠폰 ID 목록에 해당하는 쿠폰들의 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 쿠폰 정보 조회",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponInfoResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )

    public List<CouponInfoResponse> getAllByCouponIdList(@Parameter(description = "쿠폰 ID 목록", required = true) @RequestParam List<Long> couponIdList) {
        return couponService.getAllByCouponIdList(couponIdList);
    }

}
