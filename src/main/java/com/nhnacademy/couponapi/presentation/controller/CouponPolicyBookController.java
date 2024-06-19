package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.CouponPolicyBookService;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyBookRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyBookResponseDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<CouponPolicyBookResponseDTO>> findAll() {
        List<CouponPolicyBookResponseDTO> couponPolicyBooks = couponPolicyBookService.findAllCouponPolicyBooks();
        return ResponseEntity.ok(couponPolicyBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> find(@PathVariable Long id) {
        CouponPolicyBookResponseDTO couponPolicyBook = couponPolicyBookService.findCouponPolicyBookById(id);
        return ResponseEntity.ok(couponPolicyBook);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyBookResponseDTO> create(@RequestBody @Valid CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponPolicyBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> update(@PathVariable @Valid Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO updatedCouponPolicyBook = couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
        return ResponseEntity.ok(updatedCouponPolicyBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponPolicyBookService.deleteCouponPolicyBook(id);
        return ResponseEntity.noContent().build();
    }
}
