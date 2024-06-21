package com.nhnacademy.couponapi.presentation.controller;

import com.nhnacademy.couponapi.application.service.UserCouponService;
import com.nhnacademy.couponapi.presentation.dto.request.UserCouponRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponUserListResponseDTO;
import com.nhnacademy.couponapi.presentation.dto.response.UserCouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserCouponController 클래스는 사용자 쿠폰 관리를 위한 RESTful API 엔드포인트를 제공합니다.
 */
@Tag(name = "User Coupon API", description = "사용자 쿠폰 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    /**
     * 특정 사용자의 쿠폰 목록을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID.
     * @return CouponUserListResponseDTO 객체 목록을 포함하는 List.
     */
    @Operation(summary = "사용자 쿠폰 목록 조회", description = "특정 사용자의 쿠폰 목록을 조회합니다.")
    @GetMapping("/user")
    public List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId) {
        return userCouponService.findUserCoupons(userId);
    }

//    /**
//     * 모든 사용자 쿠폰 목록을 조회합니다.
//     *
//     * @return UserCouponResponseDTO 객체 목록을 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "모든 사용자 쿠폰 목록 조회", description = "모든 사용자 쿠폰 목록을 조회합니다.")
//    @GetMapping
//    public ResponseEntity<List<UserCouponResponseDTO>> findAll() {
//        List<UserCouponResponseDTO> userCoupons = userCouponService.findAllUserCoupons();
//        return ResponseEntity.ok(userCoupons);
//    }
//
//    /**
//     * 특정 ID를 가진 사용자 쿠폰을 조회합니다.
//     *
//     * @param id 조회할 사용자 쿠폰의 ID.
//     * @return UserCouponResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "사용자 쿠폰 조회", description = "특정 ID를 가진 사용자 쿠폰을 조회합니다.")
//    @GetMapping("/{id}")
//    public ResponseEntity<UserCouponResponseDTO> find(@PathVariable Long id) {
//        UserCouponResponseDTO userCoupon = userCouponService.findUserCouponById(id);
//        return ResponseEntity.ok(userCoupon);
//    }
//
//    /**
//     * 새로운 사용자 쿠폰을 생성합니다.
//     *
//     * @param userCouponRequestDTO 생성할 사용자 쿠폰의 세부 정보.
//     * @return 생성된 UserCouponResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "사용자 쿠폰 생성", description = "새로운 사용자 쿠폰을 생성합니다.")
//    @PostMapping
//    public ResponseEntity<UserCouponResponseDTO> create(@RequestBody @Valid UserCouponRequestDTO userCouponRequestDTO) {
//        UserCouponResponseDTO createdUserCoupon = userCouponService.createUserCoupon(userCouponRequestDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserCoupon);
//    }
//
//    /**
//     * 특정 ID를 가진 사용자 쿠폰을 업데이트합니다.
//     *
//     * @param id 업데이트할 사용자 쿠폰의 ID.
//     * @param userCouponRequestDTO 사용자 쿠폰의 새로운 세부 정보.
//     * @return 업데이트된 UserCouponResponseDTO 객체를 포함하는 ResponseEntity.
//     */
//    @Operation(summary = "사용자 쿠폰 업데이트", description = "특정 ID를 가진 사용자 쿠폰을 업데이트합니다.")
//    @PutMapping("/{id}")
//    public ResponseEntity<UserCouponResponseDTO> update(@PathVariable @Valid Long id, @RequestBody UserCouponRequestDTO userCouponRequestDTO) {
//        UserCouponResponseDTO updatedUserCoupon = userCouponService.updateUserCoupon(id, userCouponRequestDTO);
//        return ResponseEntity.ok(updatedUserCoupon);
//    }
//
//    /**
//     * 특정 ID를 가진 사용자 쿠폰을 삭제합니다.
//     *
//     * @param id 삭제할 사용자 쿠폰의 ID.
//     * @return 내용이 없는 ResponseEntity.
//     */
//    @Operation(summary = "사용자 쿠폰 삭제", description = "특정 ID를 가진 사용자 쿠폰을 삭제합니다.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        userCouponService.deleteUserCoupon(id);
//        return ResponseEntity.noContent().build();
//    }
}
