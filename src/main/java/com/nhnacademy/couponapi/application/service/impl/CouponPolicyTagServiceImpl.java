package com.nhnacademy.couponapi.application.service.impl;

import com.nhnacademy.couponapi.application.service.CouponPolicyTagService;
import com.nhnacademy.couponapi.application.service.CouponPolicyService;
import com.nhnacademy.couponapi.application.adapter.TagAdapter;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicy;
import com.nhnacademy.couponapi.persistence.domain.CouponPolicyTag;
import com.nhnacademy.couponapi.persistence.repository.CouponPolicyTagRepository;
import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyTagRequestDTO;
import com.nhnacademy.couponapi.presentation.dto.response.CouponPolicyTagResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponPolicyTagServiceImpl implements CouponPolicyTagService {

    private final CouponPolicyTagRepository couponPolicyTagRepository;

    private final CouponPolicyService couponPolicyService;

    private final TagAdapter tagAdapter;

    @Override
    @Transactional(readOnly = true)
    public List<CouponPolicyTagResponseDTO> getAllCouponPolicyTags() {
        return couponPolicyTagRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponPolicyTagResponseDTO getCouponPolicyTagById(Long id) {
        CouponPolicyTag couponPolicyTag = couponPolicyTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyTag not found"));
        return toResponseDTO(couponPolicyTag);
    }

    @Override
    @Transactional
    public CouponPolicyTagResponseDTO createCouponPolicyTag(CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.couponPolicyId());
        tagAdapter.getTagById(requestDTO.tagId());
        CouponPolicyTag couponPolicyTag = CouponPolicyTag.builder()
                .couponPolicy(couponPolicy)
                .tagId(requestDTO.tagId())
                .build();
        CouponPolicyTag savedCouponPolicyTag = couponPolicyTagRepository.save(couponPolicyTag);
        return toResponseDTO(savedCouponPolicyTag);
    }

    @Override
    @Transactional
    public CouponPolicyTagResponseDTO updateCouponPolicyTag(Long id, CouponPolicyTagRequestDTO requestDTO) {
        CouponPolicyTag couponPolicyTag = couponPolicyTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CouponPolicyTag not found"));
        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicyEntityById(requestDTO.couponPolicyId());
        tagAdapter.getTagById(requestDTO.tagId());
        couponPolicyTag.setCouponPolicy(couponPolicy);
        couponPolicyTag.setTagId(requestDTO.tagId());
        CouponPolicyTag updatedCouponPolicyTag = couponPolicyTagRepository.save(couponPolicyTag);
        return toResponseDTO(updatedCouponPolicyTag);
    }

    @Override
    @Transactional
    public void deleteCouponPolicyTag(Long id) {
        couponPolicyTagRepository.deleteById(id);
    }

    private CouponPolicyTagResponseDTO toResponseDTO(CouponPolicyTag couponPolicyTag) {
        return new CouponPolicyTagResponseDTO(
                couponPolicyTag.getCouponPolicyTagId(),
                couponPolicyTag.getCouponPolicy().getCouponPolicyId(),
                couponPolicyTag.getTagId()
        );
    }

}
