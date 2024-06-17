package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    @GetMapping
    public List<UserCouponResponseDTO> getAllUserCoupons() {
        return userCouponService.getAllUserCoupons();
    }

    @GetMapping("/{id}")
    public UserCouponResponseDTO getUserCouponById(@PathVariable Long id) {
        return userCouponService.getUserCouponById(id);
    }

    @PostMapping
    public UserCouponResponseDTO createUserCoupon(@RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        return userCouponService.createUserCoupon(userCouponRequestDTO);
    }

    @PutMapping("/{id}")
    public UserCouponResponseDTO updateUserCoupon(@PathVariable Long id, @RequestBody UserCouponRequestDTO userCouponRequestDTO) {
        return userCouponService.updateUserCoupon(id, userCouponRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUserCoupon(@PathVariable Long id) {
        userCouponService.deleteUserCoupon(id);
    }
}
