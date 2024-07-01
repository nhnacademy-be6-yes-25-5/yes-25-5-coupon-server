package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 쿠폰 정책 카테고리 관리 API를 위한 컨트롤러 클래스입니다.
 */
@Tag(name = "Coupon Policy Category API", description = "카테고리에 쿠폰 정책 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/categories")
public class CouponPolicyCategoryController {

    private final CouponPolicyCategoryService couponPolicyCategoryService;

    /**
     * 특정 카테고리에 관한 모든 쿠폰 정책 목록을 조회합니다.
     *
     * @return 특정 카테고리에 관한 모든 쿠폰 정책 목록
     */
    @Operation(summary = "특정 카테고리에 관한 모든 쿠폰 정책 조회", description = "특정 카테고리에 관한 모든 쿠폰 정책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponPolicyCategoryResponseDTO>> findAll() {
        List<CouponPolicyCategoryResponseDTO> couponPolicyCategories = couponPolicyCategoryService.findAllCouponPolicyCategories();
        return ResponseEntity.ok(couponPolicyCategories);
    }

    /**
     * 카테고리에 관한 새로운 쿠폰 정책을 생성합니다.
     *
     * @param requestDTO 쿠폰 정책 생성에 필요한 정보가 담긴 DTO
     * @return 생성된 쿠폰 정책 정보
     */
    @Operation(summary = "카테고리에 관한 쿠폰 정책 생성", description = "카테고리에 관한 새로운 쿠폰 정책을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<CouponPolicyCategoryResponseDTO> create(@RequestBody @Valid CouponPolicyCategoryRequestDTO requestDTO) {
        CouponPolicyCategoryResponseDTO createdCouponPolicyCategory = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyCategory);
    }
}