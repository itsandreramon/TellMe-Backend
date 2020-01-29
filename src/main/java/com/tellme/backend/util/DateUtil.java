/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.util;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class DateUtil {

    public static Instant convertStringToInstant(String timestamp) {
        try {
            return Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date: " + timestamp);
            return null;
        }
    }
}
