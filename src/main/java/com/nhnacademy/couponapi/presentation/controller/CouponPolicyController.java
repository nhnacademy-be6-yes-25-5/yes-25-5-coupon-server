package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CouponPolicyController 클래스는 쿠폰 정책 관리를 위한 RESTful API 엔드포인트를 제공합니다.
 */
@Tag(name = "Coupon Policy API", description = "쿠폰 정책 관리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons/policy")
public class CouponPolicyController {

    private final CouponPolicyService couponPolicyService;

    /**
     * 모든 쿠폰 정책 목록을 조회합니다.
     *
     * @return CouponPolicyResponseDTO 객체 목록을 포함하는 ResponseEntity.
     */
    @Operation(summary = "모든 쿠폰 정책 조회", description = "모든 쿠폰 정책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponPolicyResponseDTO>> getAll() {
        List<CouponPolicyResponseDTO> couponPolicies = couponPolicyService.getAllCouponPolicies();
        return ResponseEntity.ok(couponPolicies);
    }

    /**
     * 새로운 쿠폰 정책을 생성합니다.
     *
     * @param couponPolicyRequestDTO 생성할 쿠폰 정책의 세부 정보.
     * @return 생성된 CouponPolicyResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "쿠폰 정책 생성", description = "새로운 쿠폰 정책을 생성합니다.")
    @PostMapping("/create")
    public ResponseEntity<CouponPolicyResponseDTO> create(@RequestBody @Valid CouponPolicyRequestDTO couponPolicyRequestDTO) {
        log.info("Creating new coupon policy with request: {}", couponPolicyRequestDTO);
        CouponPolicyResponseDTO createdCouponPolicy = couponPolicyService.createCouponPolicy(couponPolicyRequestDTO);
        log.info("Created coupon policy: {}", createdCouponPolicy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicy);
    }
}