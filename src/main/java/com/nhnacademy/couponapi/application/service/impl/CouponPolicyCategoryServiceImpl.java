//package com.nhnacademy.couponapi.application.service.impl;
//
//import com.nhnacademy.couponapi.application.adapter.CategoryAdapter;
//import com.nhnacademy.couponapi.application.service.CouponPolicyCategoryService;
//import com.nhnacademy.couponapi.application.service.CouponPolicyService;
//import com.nhnacademy.couponapi.common.exception.CouponPolicyCategoryServiceException;
//import com.nhnacademy.couponapi.common.exception.payload.ErrorStatus;
//import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
//import com.nhnacademy.couponapi.persistence.domain.CouponPolicyCategory;
//import com.nhnacademy.couponapi.persistence.repository.CouponPolicyCategoryRepository;
//import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyCategoryRequestDTO;
//import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyCategoryResponseDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Transactional
//@Service
//public class CouponPolicyCategoryServiceImpl implements CouponPolicyCategoryService {
//
//    private final CouponPolicyCategoryRepository couponPolicyCategoryRepository;
//    private final CouponPolicyService couponPolicyService;
//    private final CategoryAdapter categoryAdapter;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<CouponPolicyCategoryResponseDTO> findAllCouponPolicyCategories() {
//        return couponPolicyCategoryRepository.findAll().stream()
//                .map(CouponPolicyCategoryResponseDTO::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public CouponPolicyCategoryResponseDTO createCouponPolicyCategory(CouponPolicyCategoryRequestDTO requestDTO) {
//
//        try {
//            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
//            categoryAdapter.getCategoryById(requestDTO.categoryId());
//            CouponPolicyCategory couponPolicyCategory = CouponPolicyCategory.builder()
//                    .couponPolicy(couponPolicy)
//                    .categoryId(requestDTO.categoryId())
//                    .build();
//            CouponPolicyCategory savedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);
//
//            return CouponPolicyCategoryResponseDTO.fromEntity(savedCouponPolicyCategory);
//        } catch (Exception e) {
//            throw new CouponPolicyCategoryServiceException(
//                    ErrorStatus.toErrorStatus("Coupon policy category creation failed", 500, LocalDateTime.now())
//            );
//        }
//
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public CouponPolicyCategoryResponseDTO findCouponPolicyCategoryById(Long id) {
//
//        CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
//                .orElseThrow(() -> new CouponPolicyCategoryServiceException(
//                        ErrorStatus.toErrorStatus("Coupon policy category by id", 404, LocalDateTime.now())
//                ));
//
//        return CouponPolicyCategoryResponseDTO.fromEntity(couponPolicyCategory);
//    }
//
//    @Override
//    public CouponPolicyCategoryResponseDTO updateCouponPolicyCategory(Long id, CouponPolicyCategoryRequestDTO requestDTO) {
//
//        try {
//            CouponPolicyCategory couponPolicyCategory = couponPolicyCategoryRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("CouponPolicyCategory not found"));
//            CouponPolicy couponPolicy = couponPolicyService.findCouponPolicyEntityById(requestDTO.couponPolicyId());
//            categoryAdapter.getCategoryById(requestDTO.categoryId());
//            couponPolicyCategory.updateCouponPolicy(couponPolicy);
//            couponPolicyCategory.updateCategoryId(requestDTO.categoryId());
//            CouponPolicyCategory updatedCouponPolicyCategory = couponPolicyCategoryRepository.save(couponPolicyCategory);
//
//            return CouponPolicyCategoryResponseDTO.fromEntity(updatedCouponPolicyCategory);
//        } catch (Exception e) {
//            throw new CouponPolicyCategoryServiceException(
//                    ErrorStatus.toErrorStatus("Coupon policy category update failed", 500, LocalDateTime.now())
//            );
//        }
//
//    }
//
//    @Override
//    public void deleteCouponPolicyCategory(Long id) {
//        try {
//            couponPolicyCategoryRepository.deleteById(id);
//        } catch (Exception e) {
//            throw new CouponPolicyCategoryServiceException(
//                    ErrorStatus.toErrorStatus("Coupon policy category delete failed", 500, LocalDateTime.now())
//            );
//        }
//    }
//
//}
