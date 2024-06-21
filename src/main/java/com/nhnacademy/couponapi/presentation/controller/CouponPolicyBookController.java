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
 * CouponPolicyBookController 클래스는 도서에 관한 쿠폰 정책 관리를 위한 RESTful API 엔드포인트를 제공합니다.
 */
@Tag(name = "Coupon Policy Book API", description = "도서에 쿠폰 정책 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-books")
public class CouponPolicyBookController {

    private final CouponPolicyBookService couponPolicyBookService;

    /**
     * 모든 도서에 관한 쿠폰 정책 목록을 조회합니다.
     *
     * @return CouponPolicyBookResponseDTO 객체 목록을 포함하는 ResponseEntity.
     */
    @Operation(summary = "특정 도서에 관한 모든 쿠폰 정책 조회", description = "특정 도서에 관한 모든 쿠폰 정책 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CouponPolicyBookResponseDTO>> findAll() {
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = couponPolicyBookService.findAllCouponPolicyBooks();
        return ResponseEntity.ok(couponPolicyBooks);
    }

    /**
     * 특정 도서 ID를 가진 쿠폰 정책을 조회합니다.
     *
     * @param id 조회할 쿠폰 정책 도서 ID.
     * @return CouponPolicyBookResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "도서에 관한 쿠폰 정책 조회", description = "특정 도서 ID를 가진 도서에 관한 쿠폰 정책을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> find(@PathVariable Long id) {
        CouponPolicyBookResponseDTO couponPolicyBook = couponPolicyBookService.findCouponPolicyBookById(id);
        return ResponseEntity.ok(couponPolicyBook);
    }

    /**
     * 도서에 관한 새로운 쿠폰 정책을 생성합니다.
     *
     * @param requestDTO  도서에 관한 생성할 쿠폰 정책의 세부 정보.
     * @return 생성된 CouponPolicyBookResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "도서에 관한 쿠폰 정책 생성", description = "도서에 관한 새로운 쿠폰 정책을 생성합니다.")
    @PostMapping
    public ResponseEntity<CouponPolicyBookResponseDTO> create(@RequestBody @Valid CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }

    /**
     * 도서 ID를 가진 특정 도서에 관한 쿠폰 정책을 업데이트합니다.
     *
     * @param id 업데이트할 도서에 관한 쿠폰 정책의 ID.
     * @param requestDTO 특정 도서에 관한 쿠폰 정책의 새로운 세부 정보.
     * @return 업데이트된 CouponPolicyBookResponseDTO 객체를 포함하는 ResponseEntity.
     */
    @Operation(summary = "특정 도서 쿠폰 정책 업데이트", description = "특정 ID를 가진 도서에 관한 쿠폰 정책을 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO updatedCouponPolicyBook = couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyBook);
    }

    /**
     * 특정 ID를 가진 도서에관한 쿠폰 정책을 삭제합니다.
     *
     * @param id 삭제할 도서 쿠폰 정책의 ID.
     * @return 내용이 없는 ResponseEntity.
     */
    @Operation(summary = "특정 도서에 관한 쿠폰 정책 삭제", description = "특정 ID를 가진 도서 쿠폰 정책을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponPolicyBookService.deleteCouponPolicyBook(id);
        return ResponseEntity.noContent().build();
    }
}
