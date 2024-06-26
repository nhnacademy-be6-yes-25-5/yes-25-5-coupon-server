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
    public ResponseEntity<List<CouponPolicyResponseDTO>> findAll() {
        List<CouponPolicyResponseDTO> couponPolicies = couponPolicyService.findAllCouponPolicies();
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

//    /**
//     * 특정 ID를 가진 쿠폰 정책을 조회합니다.
//     *
//     * @param id 조회할 쿠폰 정책의 ID.
//     * @return CouponPolicyResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "쿠폰 정책 조회", description = "특정 ID를 가진 쿠폰 정책을 조회합니다.")
//    @GetMapping("/{id}")
//    public ResponseEntity<CouponPolicyResponseDTO> find(@PathVariable Long id) {
//        CouponPolicyResponseDTO couponPolicy = couponPolicyService.findCouponPolicyById(id);
//        return ResponseEntity.ok(couponPolicy);
//    }

    /**
     * 새로운 쿠폰 정책을 생성합니다.
     *
     * @param couponPolicyRequestDTO 생성할 쿠폰 정책의 세부 정보.
     * @return 생성된 CouponPolicyResponseDTO 객체를 포함하는 ResponseEntity.
     */
//
//    /**
//     * 특정 ID를 가진 쿠폰 정책을 업데이트합니다.
//     *
//     * @param id 업데이트할 쿠폰 정책의 ID.
//     * @param couponPolicyRequestDTO 쿠폰 정책의 새로운 세부 정보.
//     * @return 업데이트된 CouponPolicyResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "쿠폰 정책 업데이트", description = "특정 ID를 가진 쿠폰 정책을 업데이트합니다.")
//    @PutMapping("/{id}")
//    public ResponseEntity<CouponPolicyResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyRequestDTO couponPolicyRequestDTO) {
//        CouponPolicyResponseDTO updatedCouponPolicy = couponPolicyService.updateCouponPolicy(id, couponPolicyRequestDTO);
//        return ResponseEntity.ok(updatedCouponPolicy);
//    }
//
//    /**
//     * 특정 ID를 가진 쿠폰 정책을 삭제합니다.
//     *
//     * @param id 삭제할 쿠폰 정책의 ID.
//     * @return 내용이 없는 ResponseEntity.
//     */
//    @Operation(summary = "쿠폰 정책 삭제", description = "특정 ID를 가진 쿠폰 정책을 삭제합니다.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        couponPolicyService.deleteCouponPolicy(id);
//        return ResponseEntity.noContent().build();
//    }
}
