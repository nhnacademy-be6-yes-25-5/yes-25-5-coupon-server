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
    public ResponseEntity<List<UserCouponResponseDTO>> getAllUserCoupons() {
        List<UserCouponResponseDTO> userCoupons = userCouponService.getAllUserCoupons();
        return new ResponseEntity<>(userCoupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCouponResponseDTO> getUserCouponById(@PathVariable Long id) {
        UserCouponResponseDTO userCoupon = userCouponService.getUserCouponById(id);
        return new ResponseEntity<>(userCoupon, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCouponResponseDTO> createUserCoupon(@RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        UserCouponResponseDTO createdUserCoupon = userCouponService.createUserCoupon(userCouponRequestDTO);
        return new ResponseEntity<>(createdUserCoupon, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCouponResponseDTO> updateUserCoupon(@PathVariable Long id, @RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        UserCouponResponseDTO updatedUserCoupon = userCouponService.updateUserCoupon(id, userCouponRequestDTO);
        return new ResponseEntity<>(updatedUserCoupon, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCoupon(@PathVariable Long id) {
        userCouponService.deleteUserCoupon(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
