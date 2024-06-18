package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policy-books")
public class CouponPolicyBookController {

    private final CouponPolicyBookService couponPolicyBookService;

    @GetMapping
    public ResponseEntity<List<CouponPolicyBookResponseDTO>> getAllCouponPolicyBooks() {
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = couponPolicyBookService.getAllCouponPolicyBooks();
        return ResponseEntity.ok(couponPolicyBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> getCouponPolicyBookById(@PathVariable Long id) {
        CouponPolicyBookResponseDTO couponPolicyBook = couponPolicyBookService.getCouponPolicyBookById(id);
        return ResponseEntity.ok(couponPolicyBook);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyBookResponseDTO> createCouponPolicyBook(@RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> updateCouponPolicyBook(@PathVariable Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO updatedCouponPolicyBook = couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicyBook(@PathVariable Long id) {
        couponPolicyBookService.deleteCouponPolicyBook(id);
        return ResponseEntity.noContent().build();
    }
}
