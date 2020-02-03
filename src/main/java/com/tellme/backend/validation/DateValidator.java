/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

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
        if (timestamp.isEmpty()) {
            return true;
        } else {
            return DateUtil.convertStringToInstant(timestamp) != null;
        }
    }
}
