package com.nhnacademy.couponapi.validation;

import com.nhnacademy.couponapi.presentation.dto.request.CouponPolicyRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class EitherOrValidator implements ConstraintValidator<EitherOr, CouponPolicyRequestDTO> {

    @Override
    public boolean isValid(CouponPolicyRequestDTO dto, ConstraintValidatorContext context) {
        BigDecimal discountValue = dto.couponPolicyDiscountValue();
        BigDecimal rate = dto.couponPolicyRate();
        return (discountValue == null) != (rate == null);
    }
}