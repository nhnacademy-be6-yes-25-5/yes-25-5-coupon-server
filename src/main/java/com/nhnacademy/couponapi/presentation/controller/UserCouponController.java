package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    @GetMapping
    public ResponseEntity<List<UserCouponResponseDTO>> findAll() {
        List<UserCouponResponseDTO> userCoupons = userCouponService.findAllUserCoupons();
        return ResponseEntity.ok(userCoupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCouponResponseDTO> find(@PathVariable Long id) {
        UserCouponResponseDTO userCoupon = userCouponService.findUserCouponById(id);
        return ResponseEntity.ok(userCoupon);
    }

    @PostMapping
    public ResponseEntity<UserCouponResponseDTO> create(@RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        UserCouponResponseDTO createdUserCoupon = userCouponService.createUserCoupon(userCouponRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserCoupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCouponResponseDTO> update(@PathVariable Long id, @RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        UserCouponResponseDTO updatedUserCoupon = userCouponService.updateUserCoupon(id, userCouponRequestDTO);
        return ResponseEntity.ok(updatedUserCoupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userCouponService.deleteUserCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
