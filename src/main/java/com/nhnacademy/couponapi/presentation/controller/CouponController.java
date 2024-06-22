package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.application.service.CouponService;
import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon API", description = "쿠폰 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @Operation(summary = "모든 쿠폰 조회", description = "모든 쿠폰 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponUserListResponseDTO>> findAll() {
        List<CouponUserListResponseDTO> coupons = couponService.findAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @Operation(summary = "쿠폰 조회", description = "특정 ID를 가진 쿠폰을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> find(@PathVariable Long id) {
        CouponResponseDTO coupon = couponService.findCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    @Operation(summary = "쿠폰 생성", description = "새로운 쿠폰을 생성합니다.")
    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@RequestBody @Valid CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO createdCoupon = couponService.createCoupon(couponRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }

    @Operation(summary = "쿠폰 업데이트", description = "특정 ID를 가진 쿠폰을 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponRequestDTO couponRequestDTO) {
        CouponResponseDTO updatedCoupon = couponService.updateCoupon(id, couponRequestDTO);
        return ResponseEntity.ok(updatedCoupon);
    }

    @Operation(summary = "쿠폰 삭제", description = "특정 ID를 가진 쿠폰을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "웰컴 쿠폰 발급", description = "사용자에게 웰컴 쿠폰을 발급합니다.")
    @PostMapping("/welcome/{userId}")
    public ResponseEntity<Void> issueWelcomeCoupon(@PathVariable Long userId) {
        userCouponService.issueWelcomeCoupon(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}