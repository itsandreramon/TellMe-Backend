package com.tellme.backend.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public void initialize(ValidDate contactNumber) {
        // ...
    }

    @Override
    public boolean isValid(String timestamp, ConstraintValidatorContext cxt) {
        var valid = true;

        try {
            DateUtils.convertStringToDate(timestamp);
        } catch (Exception e) {
            valid = false;
        }

        return valid;
    }

}