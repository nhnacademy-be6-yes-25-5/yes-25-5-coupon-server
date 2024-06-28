//package com.nhnacademy.couponapi.presentation.controller;
//
//import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
//import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * CouponPolicyCategoryController 클래스는 카테고리에 관한 쿠폰 정책 관리를 위한 RESTful API 엔드포인트를 제공합니다.
// */
//@Tag(name = "Coupon Policy Category API", description = "쿠폰 정책 카테고리 관리 API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/coupons/policy/categories")
//public class CouponPolicyCategoryController {
//
//    private final CouponPolicyCategoryService couponPolicyCategoryService;
//
//    /**
//     * 카테고리에 관한 모든 쿠폰 정책 목록을 조회합니다.
//     *
//     * @return CouponPolicyCategoryResponseDTO 객체 목록을 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "카테고리에 관한 모든 쿠폰 정책 조회", description = "카테고리에 관한 모든 쿠폰 정책 목록을 조회합니다.")
//    @GetMapping
//    public ResponseEntity<List<CouponPolicyCategoryResponseDTO>> findAll() {
//        List<CouponPolicyCategoryResponseDTO> couponPolicyCategories = couponPolicyCategoryService.findAllCouponPolicyCategories();
//        return ResponseEntity.ok(couponPolicyCategories);
//    }
//
//    /**
//     * 카테고리에 관한 새로운 쿠폰 정책을 생성합니다.
//     *
//     * @param requestDTO 카테고리에 관한 생성할 쿠폰 정책의 세부 정보.
//     * @return 생성된 CouponPolicyCategoryResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "카테고리에 관한 쿠폰 정책 생성", description = "카테고리에 관한 새로운 쿠폰 정책을 생성합니다.")
//    @PostMapping("/create")
//    public ResponseEntity<CouponPolicyCategoryResponseDTO> create(@RequestBody @Valid CouponPolicyCategoryRequestDTO requestDTO) {
//        CouponPolicyCategoryResponseDTO createdCouponPolicyCategory = couponPolicyCategoryService.createCouponPolicyCategory(requestDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyCategory);
//    }
//
//    /**
//     * 특정 ID를 가진 카테고리에 관한 쿠폰 정책을 조회합니다.
//     *
//     * @param id 조회할 카테고리에 관한 쿠폰 정책 ID.
//     * @return CouponPolicyCategoryResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "카테고리에 관한 쿠폰 정책 조회", description = "특정 ID를 가진 카테고리에 관한 쿠폰 정책을 조회합니다.")
//    @GetMapping("/{id}")
//    public ResponseEntity<CouponPolicyCategoryResponseDTO> find(@PathVariable Long id) {
//        CouponPolicyCategoryResponseDTO couponPolicyCategory = couponPolicyCategoryService.findCouponPolicyCategoryById(id);
//        return ResponseEntity.ok(couponPolicyCategory);
//    }
//
//    /**
//     * 카테고리에 관한 특정 ID를 가진 쿠폰 정책을 업데이트합니다.
//     *
//     * @param id 업데이트할 카테고리에 관한 쿠폰 정책 ID.
//     * @param requestDTO 카테고리에 관한 쿠폰 정책의 새로운 세부 정보.
//     * @return 업데이트된 CouponPolicyCategoryResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "카테고리에 관한 쿠폰 정책 업데이트", description = "카테고리에 관한 특정 ID를 가진 쿠폰 정책을 업데이트합니다.")
//    @PutMapping("/{id}")
//    public ResponseEntity<CouponPolicyCategoryResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyCategoryRequestDTO requestDTO) {
//        CouponPolicyCategoryResponseDTO updatedCouponPolicyCategory = couponPolicyCategoryService.updateCouponPolicyCategory(id, requestDTO);
//        return ResponseEntity.ok(updatedCouponPolicyCategory);
//    }
//
//    /**
//     * 특정 ID를 가진 쿠폰 정책 카테고리를 삭제합니다.
//     *
//     * @param id 삭제할 쿠폰 정책 카테고리의 ID.
//     * @return 내용이 없는 ResponseEntity.
//     */
//    @Operation(summary = "카테고리에 관한 쿠폰 정책 삭제", description = "카테고리에 관한 특정 ID를 가진 쿠폰 정책을 삭제합니다.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        couponPolicyCategoryService.deleteCouponPolicyCategory(id);
//        return ResponseEntity.noContent().build();
//    }
//}
