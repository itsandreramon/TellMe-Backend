package com.tellme.backend.validation;

import com.tellme.backend.util.DateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public void initialize(ValidDate contactNumber) {
        // ...
    }

    @Override
    public boolean isValid(String timestamp, ConstraintValidatorContext cxt) {
        boolean valid = true;

        try {
            DateUtil.convertStringToDate(timestamp);
        } catch (Exception e) {
            valid = false;
        }

        return valid;
    }
}
