package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 쿠폰 정책 도서 관리 API를 위한 컨트롤러 클래스입니다.
 */
@Tag(name = "Coupon Policy Book API", description = "도서에 쿠폰 정책 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/books")
public class CouponPolicyBookController {

    private final CouponPolicyBookService couponPolicyBookService;

    /**
     * 특정 도서에 관한 모든 쿠폰 정책 목록을 조회합니다.
     *
     * @return 특정 도서에 관한 모든 쿠폰 정책 목록
     */
    @Operation(
            summary = "특정 도서에 관한 모든 쿠폰 정책 조회",
            description = "특정 도서에 관한 모든 쿠폰 정책 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 쿠폰 정책 목록 조회",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponPolicyBookResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "쿠폰 정책을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @GetMapping
    public ResponseEntity<List<CouponPolicyBookResponseDTO>> getAll() {
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = couponPolicyBookService.getAllCouponPolicyBooks();
        return ResponseEntity.ok(couponPolicyBooks);
    }

    /**
     * 도서에 관한 새로운 쿠폰 정책을 생성합니다.
     *
     * @param requestDTO 쿠폰 정책 생성에 필요한 정보가 담긴 DTO
     * @return 생성된 쿠폰 정책 정보
     */
    @Operation(
            summary = "도서에 관한 쿠폰 정책 생성",
            description = "도서에 관한 새로운 쿠폰 정책을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "성공적으로 쿠폰 정책 생성",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CouponPolicyBookResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<CouponPolicyBookResponseDTO> create(
            @Parameter(description = "쿠폰 정책 생성에 필요한 정보가 담긴 DTO", required = true)
            @RequestBody @Valid CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }
}