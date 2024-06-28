package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
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

//    @Operation(summary = "도서에 관한 쿠폰 정책 조회", description = "특정 도서 ID를 가진 도서에 관한 쿠폰 정책을 조회합니다.")
//    @GetMapping("/{id}")
//    public ResponseEntity<CouponPolicyResponseDTO> find(@PathVariable Long id) {
//        CouponPolicyResponseDTO couponPolicyBook = couponPolicyBookService.findCouponPolicyBookById(id);
//        return ResponseEntity.ok(couponPolicyBook);
//    }
//
//    @Operation(summary = "특정 도서 쿠폰 정책 업데이트", description = "특정 ID를 가진 도서에 관한 쿠폰 정책을 업데이트합니다.")
//    @PutMapping("/{id}")
//    public ResponseEntity<CouponPolicyResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
//        CouponPolicyResponseDTO updatedCouponPolicyBook = couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
//        return ResponseEntity.ok(updatedCouponPolicyBook);
//    }
//
//    @Operation(summary = "특정 도서에 관한 쿠폰 정책 삭제", description = "특정 ID를 가진 도서 쿠폰 정책을 삭제합니다.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        couponPolicyBookService.deleteCouponPolicyBook(id);
//        return ResponseEntity.noContent().build();
//    }
}