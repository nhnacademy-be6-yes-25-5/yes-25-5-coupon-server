package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "특정 도서에 관한 모든 쿠폰 정책 조회", description = "특정 도서에 관한 모든 쿠폰 정책 목록을 조회합니다.")
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
    @Operation(summary = "도서에 관한 쿠폰 정책 생성", description = "도서에 관한 새로운 쿠폰 정책을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<CouponPolicyBookResponseDTO> create(@RequestBody @Valid CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }

}