package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Coupon API", description = "사용자 쿠폰 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    @Operation(summary = "사용자 쿠폰 목록 조회", description = "특정 사용자의 쿠폰 목록을 조회합니다.")
    @GetMapping("/user")
    public List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId) {
        return userCouponService.findUserCoupons(userId);
    }

//    @PostMapping("/issue-birthday/{userId}")
//    public ResponseEntity<Void> issueBirthdayCouponForUser(@PathVariable Long userId) {
//        userCouponService.issueBirthdayCouponForUser(userId);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    } 특정사용자에게 생일쿠폰 발급 -> 테스트용
}
