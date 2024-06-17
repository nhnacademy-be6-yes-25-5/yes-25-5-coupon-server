package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.application.adapter.TagAdapter;
import com.nhnacademy.couponapi.persistance.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistance.domain.CouponPolicyTag;
import com.nhnacademy.couponapi.persistance.repository.CouponPolicyTagRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponPolicyTagServiceImpl implements CouponPolicyTagService {

    private final CouponPolicyTagRepository couponPolicyTagRepository;

    private final CouponPolicyService couponPolicyService;

    private final TagAdapter tagAdapter;

    @Override
    public List<CouponPolicyTagResponseDTO> getAllCouponPolicyTags() {
        return couponPolicyTagRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CouponPolicyTagResponseDTO getCouponPolicyTagById(Long id) {
        CouponPolicyTag couponPolicyTag = couponPolicyTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyTag not found"));
        return toResponseDTO(couponPolicyTag);
    }

    @Override
    public CouponPolicyTagResponseDTO createCouponPolicyTag(CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.getCouponPolicyId());
        tagAdapter.getTagById(requestDTO.getTagId()); // Verify tag exists
        CouponPolicyTag couponPolicyTag = CouponPolicyTag.builder()
                .couponPolicy(couponPolicy)
                .tagId(requestDTO.getTagId())
                .build();
        CouponPolicyTag savedCouponPolicyTag = couponPolicyTagRepository.save(couponPolicyTag);
        return toResponseDTO(savedCouponPolicyTag);
    }

    @Override
    public CouponPolicyTagResponseDTO updateCouponPolicyTag(Long id, CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTag couponPolicyTag = couponPolicyTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyTag not found"));
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.getCouponPolicyId());
        tagAdapter.getTagById(requestDTO.getTagId()); // Verify tag exists
        couponPolicyTag.setCouponPolicy(couponPolicy);
        couponPolicyTag.setTagId(requestDTO.getTagId());
        CouponPolicyTag updatedCouponPolicyTag = couponPolicyTagRepository.save(couponPolicyTag);
        return toResponseDTO(updatedCouponPolicyTag);
    }

    @Override
    public void deleteCouponPolicyTag(Long id) {
        couponPolicyTagRepository.deleteById(id);
    }

    private CouponPolicyTagResponseDTO toResponseDTO(CouponPolicyTag couponPolicyTag) {
        return CouponPolicyTagResponseDTO.builder()
                .couponPolicyTagId(couponPolicyTag.getCouponPolicyTagId())
                .couponPolicyId(couponPolicyTag.getCouponPolicy().getCouponPolicyId())
                .tagId(couponPolicyTag.getTagId())
                .build();
    }
}
