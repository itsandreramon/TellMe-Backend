/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.validation;

import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtil {

    public static void validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (!validator.validate(user).isEmpty()) {
            throw new IllegalStateException();
        }
    }

    public static void validate(Tell tell) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (!validator.validate(tell).isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
