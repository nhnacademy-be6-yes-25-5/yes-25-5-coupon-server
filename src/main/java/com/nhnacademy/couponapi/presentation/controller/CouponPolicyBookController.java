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
        return new ResponseEntity<>(couponPolicyBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> getCouponPolicyBookById(@PathVariable Long id) {
        CouponPolicyBookResponseDTO couponPolicyBook = couponPolicyBookService.getCouponPolicyBookById(id);
        return new ResponseEntity<>(couponPolicyBook, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponPolicyBookResponseDTO> createCouponPolicyBook(@RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO createdCouponPolicyBook = couponPolicyBookService.createCouponPolicyBook(requestDTO);
        return new ResponseEntity<>(createdCouponPolicyBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponPolicyBookResponseDTO> updateCouponPolicyBook(@PathVariable Long id, @RequestBody CouponPolicyBookRequestDTO requestDTO) {
        CouponPolicyBookResponseDTO updatedCouponPolicyBook = couponPolicyBookService.updateCouponPolicyBook(id, requestDTO);
        return new ResponseEntity<>(updatedCouponPolicyBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCouponPolicyBook(@PathVariable Long id) {
        couponPolicyBookService.deleteCouponPolicyBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
