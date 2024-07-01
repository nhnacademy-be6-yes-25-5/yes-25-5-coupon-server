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

@Tag(name = "Coupon Policy Book API", description = "도서에 쿠폰 정책 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/books")
public class CouponPolicyBookController {

    private final CouponPolicyBookService couponPolicyBookService;

    @Operation(summary = "특정 도서에 관한 모든 쿠폰 정책 조회", description = "특정 도서에 관한 모든 쿠폰 정책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponPolicyBookResponseDTO>> findAll() {
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = couponPolicyBookService.findAllCouponPolicyBooks();
        return ResponseEntity.ok(couponPolicyBooks);
    }

    @Operation(summary = "도서에 관한 쿠폰 정책 생성", description = "도서에 관한 새로운 쿠폰 정책을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<CouponPolicyBookResponseDTO> create(@RequestBody @Valid CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }

}