package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CouponPolicyTagController 클래스는 쿠폰 정책 태그 관리를 위한 RESTful API 엔드포인트를 제공합니다.
 */
@Tag(name = "Coupon Policy Tag API", description = "쿠폰 정책 태그 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-tags")
public class CouponPolicyTagController {

    private final CouponPolicyTagService couponPolicyTagService;

    /**
     * 모든 쿠폰 정책 태그 목록을 조회합니다.
     *
     * @return CouponPolicyTagResponseDTO 객체 목록을 포함하는 ResponseEntity.
     */
    @Operation(summary = "모든 쿠폰 정책 태그 조회", description = "모든 쿠폰 정책 태그 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponPolicyTagResponseDTO>> findAll() {
        List<CouponPolicyTagResponseDTO> couponPolicyTags = couponPolicyTagService.findAllCouponPolicyTags();
        return ResponseEntity.ok(couponPolicyTags);
    }

    /**
     * 특정 ID를 가진 쿠폰 정책 태그를 조회합니다.
     *
     * @param id 조회할 쿠폰 정책 태그의 ID.
     * @return CouponPolicyTagResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "쿠폰 정책 태그 조회", description = "특정 ID를 가진 쿠폰 정책 태그를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> find(@PathVariable Long id) {
        CouponPolicyTagResponseDTO couponPolicyTag = couponPolicyTagService.findCouponPolicyTagById(id);
        return ResponseEntity.ok(couponPolicyTag);
    }

    /**
     * 새로운 쿠폰 정책 태그를 생성합니다.
     *
     * @param requestDTO 생성할 쿠폰 정책 태그의 세부 정보.
     * @return 생성된 CouponPolicyTagResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "쿠폰 정책 태그 생성", description = "새로운 쿠폰 정책 태그를 생성합니다.")
    @PostMapping
    public ResponseEntity<CouponPolicyTagResponseDTO> create(@RequestBody @Valid CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO createdCouponPolicyTag = couponPolicyTagService.createCouponPolicyTag(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyTag);
    }

    /**
     * 특정 ID를 가진 쿠폰 정책 태그를 업데이트합니다.
     *
     * @param id 업데이트할 쿠폰 정책 태그의 ID.
     * @param requestDTO 쿠폰 정책 태그의 새로운 세부 정보.
     * @return 업데이트된 CouponPolicyTagResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "쿠폰 정책 태그 업데이트", description = "특정 ID를 가진 쿠폰 정책 태그를 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyTagResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTagResponseDTO updatedCouponPolicyTag = couponPolicyTagService.updateCouponPolicyTag(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyTag);
    }

    /**
     * 특정 ID를 가진 쿠폰 정책 태그를 삭제합니다.
     *
     * @param id 삭제할 쿠폰 정책 태그의 ID.
     * @return 내용이 없는 ResponseEntity.
     */
    @Operation(summary = "쿠폰 정책 태그 삭제", description = "특정 ID를 가진 쿠폰 정책 태그를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponPolicyTagService.deleteCouponPolicyTag(id);
        return ResponseEntity.noContent().build();
    }
}
